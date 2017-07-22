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

public class PieceKing extends Piece {

	private MoveGenerator mHlpr;
	private boolean wasMoved;

	public PieceKing(PieceColour colour) {
		super(colour, PieceType.KING);
		mHlpr = new MoveGenerator();
		wasMoved = false;
	}
	

	@Override
	public String printPiece() {
		return (super.getColor() == PieceColour.WHITE) ? "   KI   |" : "   ki   |";
	}

	@Override
	public List<List<Move>> movesInRange(XY cell) {
		List<List<Move>> moves = new ArrayList<>();
		moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NW, MoveType.UNDECIDED, 1));
		moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NORTH, MoveType.UNDECIDED, 1));
		moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NE, MoveType.UNDECIDED, 1));
		moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SE, MoveType.UNDECIDED, 1));
		moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SOUTH, MoveType.UNDECIDED, 1));
		moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SW, MoveType.UNDECIDED, 1));

		if (!wasMoved) {
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.EAST, MoveType.CASTLING, 2));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.WEST, MoveType.CASTLING, 2));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.EAST, MoveType.UNDECIDED, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.WEST, MoveType.UNDECIDED, 1));
		} else {
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.EAST, MoveType.UNDECIDED, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.WEST, MoveType.UNDECIDED, 1));
		}
		
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
