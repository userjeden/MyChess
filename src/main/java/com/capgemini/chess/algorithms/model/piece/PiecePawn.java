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

public class PiecePawn extends Piece {

	private MoveGenerator mHlpr;
	private boolean wasMoved;

	public PiecePawn(PieceColour colour) {
		super(colour, PieceType.PAWN);
		mHlpr = new MoveGenerator();
		wasMoved = false;
	}

	
	@Override
	public String printPiece() {
		return (super.getColor() == PieceColour.WHITE) ? "   PA   |" : "   pa   |";
	}

	@Override
	public List<List<Move>> movesInRange(XY cell) {
		List<List<Move>> moves = new ArrayList<>();

		if (!wasMoved && (super.getColor() == PieceColour.WHITE)) {
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NORTH, MoveType.EN_PASSANT_INITIAL, 2));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NORTH, MoveType.REGULAR, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NE, MoveType.CAPTURE, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NW, MoveType.CAPTURE, 1));

		} else if (super.getColor() == PieceColour.WHITE) {
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NORTH, MoveType.REGULAR, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NE, MoveType.CAPTURE, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.NW, MoveType.CAPTURE, 1));
		}

		if (!wasMoved && (super.getColor() == PieceColour.BLACK)) {
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SOUTH, MoveType.EN_PASSANT_INITIAL, 2));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SOUTH, MoveType.REGULAR, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SE, MoveType.CAPTURE, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SW, MoveType.CAPTURE, 1));

		} else if (super.getColor() == PieceColour.BLACK) {
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SOUTH, MoveType.REGULAR, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SE, MoveType.CAPTURE, 1));
			moves.add(mHlpr.limListOfMovesInDirect(this, cell, XYdirection.SW, MoveType.CAPTURE, 1));
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
