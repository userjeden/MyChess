package com.capgemini.chess.algorithms.model.board;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.StaticValues;
import com.capgemini.chess.algorithms.enums.BoardState;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.Piece;

/**
 * Board representation.
 * A single state of the game in time.
 */

public class BoardSnapshot {

	private static int SIZE = StaticValues.BOARD_SIZE;
	private BoardSnapshotGUI bHlpr;
	
	private Piece[][] board = new Piece[SIZE][SIZE];
	private BoardState boardState = BoardState.REGULAR;
	
	public BoardSnapshot(){
		this.bHlpr = new BoardSnapshotGUI();
	}
	
	
	public BoardState getBoardState() {
		return boardState;
	}

	public void setBoardState(BoardState boardState) {
		this.boardState = boardState;
	}
	
	public void setPieceAt(Piece piece, XY coord){
		this.board[coord.getX()][verticalMirror(coord.getY())] = piece;
	}
	
	public Piece getPieceAt(XY coord){
		return board[coord.getX()][verticalMirror(coord.getY())]; 
	}
	
	public boolean isCellEmpty(XY coord){
		return (board[coord.getX()][verticalMirror(coord.getY())].getType() == PieceType.EMPTY) ? true : false;
	}
	
	public boolean isCellOccupied(XY coord){
		return (board[coord.getX()][verticalMirror(coord.getY())].getType() != PieceType.EMPTY) ? true : false;
	}
	
	public boolean isCellMinePiece(XY coord, PieceColour myColour){
		return board[coord.getX()][verticalMirror(coord.getY())].getColor() == myColour;
	}
	
	public boolean isCellEnemyPiece(XY coord, PieceColour givenColour){
		return (board[coord.getX()][verticalMirror(coord.getY())].getColor().getNumVal() 
				* givenColour.getNumVal() == 2) ? true : false;
	}
	
	public boolean askPieceNotMoved(XY cell){
		return !getPieceAt(cell).getWasMoved();
	}
	
	
	public List<Piece> getPiecesForColourAsList(PieceColour colour){
		List<Piece> pieces = new ArrayList<>();
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				Piece piece = getPieceAt(new XY(i, j));
				if(piece.getColor() == colour){
					pieces.add(piece);
				}
			}
		}
		return pieces;
	}
	
	
	public XY getLocationOfPiece(Piece piece){
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if(piece == this.getPieceAt(new XY(i, j))){
					return new XY(i, j);
				}
			}
		}
		return null;
	}
	
	
	public XY getLocationOfPieceWithColour(PieceType type, PieceColour colour){
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				Piece tempPiece = this.getPieceAt(new XY(i, j));
				if(tempPiece.getType() == type && tempPiece.getColor() == colour){
					return new XY(i, j);
				}
			}
		}
		return null;
	}
	
	
	public BoardSnapshot cloneBoardSnapshot(){
		BoardSnapshot boardOutput = new BoardSnapshot();
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				boardOutput.setPieceAt(this.getPieceAt(new XY (i,j)), new XY (i,j));
			}
		}
		boardOutput.setBoardState(this.getBoardState());
		return boardOutput;
	}
	
	
	public boolean areBoardsEqual(BoardSnapshot compared){
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if((this.board[i][j].getType() != compared.board[i][j].getType()) || 
						(this.board[i][j].getColor() != compared.board[i][j].getColor())){
					return false;
				}
			}
		}
		return true;
	}
	
	
	public String printBoard(){
		return bHlpr.printBoard(board);
	}
	
	
	private int verticalMirror(int y){
		return Math.abs(y - (SIZE-1));
	}
	
}
