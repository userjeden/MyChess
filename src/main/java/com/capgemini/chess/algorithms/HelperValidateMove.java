package com.capgemini.chess.algorithms;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.Piece;
import com.capgemini.chess.userconn.translator.UserTranslator;

public class HelperValidateMove {
	
	
	private List<Move> possibleMoves = new ArrayList<>();
	private List<List<Move>> movesDump = new ArrayList<>();
	
	private UserTranslator utrl = new UserTranslator();
	
	private BoardSnapshot boardSnapshot;
	private PieceColour colour;
	
	
	public HelperValidateMove(BoardSnapshot boardSnapshot, PieceColour colour){
		this.boardSnapshot = boardSnapshot;
		this.colour = colour;
		aggregatePossibleMoves();
		filterPossibleMoves();
	}
	
	
	public int numberOfPossibleMoves(){
		return possibleMoves.size();
	}
	
	public List<Move> getPotentialMoves(){
		return possibleMoves;
	}
	
	public void importSpecialMovesFromBoard(Move move){
		
		List<Move> additionalMoves = new ArrayList<>();
		if(move.getMoveType() == MoveType.EN_PASSANT_INITIAL){
			int x = move.getIsoTargetCell().getX();
			int ya = move.getIsoTargetCell().getY();
			int yb = move.getIsoSourceCell().getY();
			XY enpassantMarker = new XY(x, ya);
			
			XY enpassantMarkerB = new XY(x, (ya+yb)/2);
			
			for(Move m : possibleMoves){
				if(m.getMovedPiece().getType() == PieceType.PAWN 
						&& m.getIsoSourceCell().getY() == enpassantMarker.getY()
						&& (m.getIsoSourceCell().getX() == enpassantMarker.getX()-1 
						|| m.getIsoSourceCell().getX() == enpassantMarker.getX()+1)){
					
					Move mov = new Move(m.getMovedPiece(), m.getIsoSourceCell(), enpassantMarkerB, MoveType.EN_PASSANT_RESPONSE);
					additionalMoves.add(mov);
				}
			}
		}
		
		for(Move m: additionalMoves){
			possibleMoves.add(m);
		}
	}
	
	
	
	
	public Move isSuchMoveAvailable(XY from, XY dest){
		for(Move m : possibleMoves){
			if(m.getIsoSourceCell().equals(from) && m.getIsoTargetCell().equals(dest)){
				return m;
			}
		}
		return null;
	}
	
	
	public Boolean isPositionUnderAttack(XY position){
		for(Move m : possibleMoves){
			if(m.getIsoTargetCell().equals(position) && m.getMoveType() == MoveType.CAPTURE){
				return true;
			}
		}
		return false;
	}
	
	
	public void printoutPossibleMoves(){
		System.out.println("List of possible moves: \n");
		for(Move move : possibleMoves){
			if(move != null && move.getMoveType() == MoveType.UNDECIDED){
				move.setMoveType(MoveType.REGULAR);
			}
			System.out.println("from: " + utrl.translateXYtoUserReadable(move.getIsoSourceCell()) + 
					" to: " + utrl.translateXYtoUserReadable(move.getIsoTargetCell()) + " type: " + move.getMoveType());
		}
	}
	
	
	private void aggregatePossibleMoves(){
		List<Piece> pieces = boardSnapshot.getPiecesForColourAsList(colour);
		for(Piece piece : pieces){
			XY cell = boardSnapshot.getLocationOfPiece(piece);
			List<List<Move>> tempMoves = piece.movesInRange(cell);
			for(List<Move> track : tempMoves){
				if(track.size() > 0){
					movesDump.add(track);
				}
			}
		}
	}


	private void filterPossibleMoves() {
		
		for(List<Move> track : movesDump){
			
			if(track.get(0).getMoveType() == MoveType.EN_PASSANT_INITIAL){
				Move temp = filterOutEnpassantTracks(track);
				if(temp != null){
					possibleMoves.add(temp);
				}
				continue;
			}
			
			if(track.get(0).getMoveType() == MoveType.CASTLING){
				Move temp = filterOutCastlingTracks(track);
				if(temp != null){
					possibleMoves.add(temp);
				}
				continue;
			}
			
			boolean interrupted = false;
			for(Move move : track){
				
				if(move == null || interrupted == true){
					break;
				}
				
				if(boardSnapshot.isCellEmpty(move.getIsoTargetCell()) 
						&& ((move.getMoveType() == MoveType.UNDECIDED)
								|| move.getMoveType() == MoveType.REGULAR)){
					
					move.setMoveType(MoveType.REGULAR);
					possibleMoves.add(move);
				}
				
				if(boardSnapshot.isCellEnemyPiece(move.getIsoTargetCell(), colour) 
						&& ((move.getMoveType() == MoveType.UNDECIDED) 
								|| move.getMoveType() == MoveType.CAPTURE)){
					
					move.setMoveType(MoveType.CAPTURE);
					possibleMoves.add(move);
					interrupted = true;
				}
				
				if(boardSnapshot.isCellMinePiece(move.getIsoTargetCell(), colour)){
					interrupted = true;
				}
				
			}
		}
	}


	private Move filterOutEnpassantTracks(List<Move> track){
		int lastCell = -1;
		for(Move m : track){
			lastCell++;
			if(boardSnapshot.isCellOccupied(m.getIsoTargetCell())){
				return null;
			}
		}
		return track.get(lastCell);
	}
	
	
	private Move filterOutCastlingTracks(List<Move> track) {
		int cellNumber = -1;
		for(Move m : track){
			cellNumber++;
			if(boardSnapshot.isCellOccupied(m.getIsoTargetCell())){
				return null;
			}
		}
		Move move = track.get(cellNumber);
		return checkPatternRockNotMoved(move) ? move : null;
	}

	
	private boolean checkPatternRockNotMoved(Move move) {
		
		Boolean rockInPlace = false;
		Boolean obstaclesRemoved = false;
		
		if(move.getMovedPiece().getColor() == PieceColour.WHITE && move.getIsoTargetCell().getX() < 4){
			rockInPlace = boardSnapshot.askPieceNotMoved(new XY(0, 0));
			obstaclesRemoved = boardSnapshot.isCellEmpty(new XY(1, 0));

		}else if(move.getMovedPiece().getColor() == PieceColour.WHITE && move.getIsoTargetCell().getX() > 4){
			rockInPlace = boardSnapshot.askPieceNotMoved(new XY(7, 0));
			obstaclesRemoved = true;
			
		}else if(move.getMovedPiece().getColor() == PieceColour.BLACK && move.getIsoTargetCell().getX() < 4){
			rockInPlace = boardSnapshot.askPieceNotMoved(new XY(0, 7));
			obstaclesRemoved = boardSnapshot.isCellEmpty(new XY(1, 7));
			
		}else if(move.getMovedPiece().getColor() == PieceColour.BLACK && move.getIsoTargetCell().getX() > 4){
			rockInPlace = boardSnapshot.askPieceNotMoved(new XY(7, 7));
			obstaclesRemoved = true;
		}
		
		return (rockInPlace && obstaclesRemoved);
	}


}


