package com.capgemini.chess.algorithms.model.piece;

import java.util.ArrayList;
import java.util.List;
import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.enums.XYdirection;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.MoveGenerator;
import com.capgemini.chess.algorithms.model.location.XY;

public class PieceBishop extends Piece {

	private MoveGenerator mHlpr;

	public PieceBishop(PieceColour colour) {
		super(colour, PieceType.BISHOP);
		mHlpr = new MoveGenerator();
	}
	

	@Override
	public String printPiece() {
		return (super.getColor() == PieceColour.WHITE) ? "   BI   |" : "   bi   |";
	}

	@Override
	public List<List<Move>> movesInRange(XY cell) {
		List<List<Move>> moves = new ArrayList<>();
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.NW, MoveType.UNDECIDED));
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.SW, MoveType.UNDECIDED));
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.NE, MoveType.UNDECIDED));
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.SE, MoveType.UNDECIDED));
		return moves;
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
