package com.capgemini.chess.algorithms.model.piece;

import java.util.List;
import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.MoveGenerator;
import com.capgemini.chess.algorithms.model.location.XY;

public class PieceKnight extends Piece {

	private MoveGenerator mHlpr;

	public PieceKnight(PieceColour colour) {
		super(colour, PieceType.KNIGHT);
		mHlpr = new MoveGenerator();
	}

	
	@Override
	public String printPiece() {
		return (super.getColor() == PieceColour.WHITE) ? "   KN   |" : "   kn   |";
	}

	@Override
	public List<List<Move>> movesInRange(XY cell) {
		int[][] input = addressTargetCells(cell);
		List<List<Move>> moves = mHlpr.listOfTracksFromList(this, cell, input, MoveType.UNDECIDED);
		return moves;
	}

	private int[][] addressTargetCells(XY cell) {
		int x = cell.getX();
		int y = cell.getY();
		int[][] input = { { x + 1, y + 2 }, { x + 2, y + 1 }, { x + 2, y - 1 }, { x + 1, y - 2 }, { x - 1, y + 2 },
				{ x - 2, y + 1 }, { x - 2, y - 1 }, { x - 1, y - 2 } };
		return input;
	}

	@Override
	public void setWasMoved() {
		// no action
	}

	@Override
	public boolean getWasMoved() {
		return true;
	}

}
