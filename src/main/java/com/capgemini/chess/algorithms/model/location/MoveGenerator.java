package com.capgemini.chess.algorithms.model.location;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.StaticValues;
import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.XYdirection;
import com.capgemini.chess.algorithms.model.piece.Piece;

public class MoveGenerator {

	private final int BOARD_SIZE = StaticValues.BOARD_SIZE;

	
	public Move singleMove(Piece piece, XY from, XY dest, MoveType type) {
		return isDestInArea(dest) ? new Move(piece, from, dest, type) : null;
	}

	
	public Move nextMoveInDirection(Piece piece, 
			XY from, XYdirection dir, MoveType type) {
		
		XY nextCell = new XY(from.getX() + dir.getdeltaX(), from.getY() + dir.getdeltaY());
		Move nextMove = singleMove(piece, from, nextCell, type);
		return nextMove;
	}

	
	public List<Move> listOfMovesInDirect(Piece piece, 
			XY from, XYdirection direction, MoveType type) {
		
		List<Move> nextMoveList = new ArrayList<>();
		Move nextMove = nextMoveInDirection(piece, from, direction, type);

		while (nextMove != null) {
			nextMoveList.add(nextMove);
			XY targetCell = nextMove.getIsoTargetCell();
			nextMove = nextMoveInDirection(piece, targetCell, direction, type);
		}

		for(Move move : nextMoveList){
			move.setIsoSourceCell(from);
		}
		
		return nextMoveList;
	}
	
	
	public List<Move> limListOfMovesInDirect(Piece piece, 
			XY from, XYdirection direction, MoveType type, int range) {
		
		List<Move> nextMoveList = new ArrayList<>();
		Move nextMove = nextMoveInDirection(piece, from, direction, type);
		int currentRange = 0;

		while ((nextMove != null) && (currentRange < range)) {
			nextMoveList.add(nextMove);
			XY targetCell = nextMove.getIsoTargetCell();
			nextMove = nextMoveInDirection(piece, targetCell, direction, type);
			currentRange++;
		}

		for(Move move : nextMoveList){
			move.setIsoSourceCell(from);
		}
		
		return nextMoveList;
	}

	
	public List<List<Move>> listOfTracksFromList(Piece piece, 
			XY from, int[][] input, MoveType type) {
		
		List<List<Move>> moves = new ArrayList<>();
		for (int i = 0; i < input.length; i++) {
			List<Move> move = new ArrayList<>();
			XY nextCell = new XY(input[i][0], input[i][1]);
			
			Move nextMove = singleMove(piece, from, nextCell, type);
			if (nextMove != null) {
				move.add(nextMove);
			}
			moves.add(move);
		}
		
		return moves;
	}


	private boolean isDestInArea(XY cell) {
		return ((cell.getX() >= 0 && cell.getX() < BOARD_SIZE) 
				&& (cell.getY() >= 0 && cell.getY() < BOARD_SIZE));
	}

}
