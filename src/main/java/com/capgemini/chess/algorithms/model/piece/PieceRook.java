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

public class PieceRook extends Piece {

	private MoveGenerator mHlpr;
	private boolean wasMoved;

	public PieceRook(PieceColour colour) {
		super(colour, PieceType.ROOK);
		mHlpr = new MoveGenerator();
		wasMoved = false;
	}

	
	@Override
	public String printPiece() {
		return (super.getColor() == PieceColour.WHITE) ? "   RO   |" : "   ro   |";
	}

	@Override
	public List<List<Move>> movesInRange(XY cell) {
		List<List<Move>> moves = new ArrayList<>();
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.NORTH, MoveType.UNDECIDED));
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.SOUTH, MoveType.UNDECIDED));
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.EAST, MoveType.UNDECIDED));
		moves.add(mHlpr.listOfMovesInDirect(this, cell, XYdirection.WEST, MoveType.UNDECIDED));
		return moves;
	}

	@Override
	public void setWasMoved() {
		wasMoved = true;
	}

	@Override
	public boolean getWasMoved() {
		return wasMoved;
	}

}
