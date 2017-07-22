package com.capgemini.chess.algorithms.model.piece;

import java.util.ArrayList;
import java.util.List;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;

public class PieceEmpty extends Piece {


	public PieceEmpty(PieceColour colour) {
		super(colour, PieceType.EMPTY);
	}

	@Override
	public String printPiece() {
		return (super.getColor() == PieceColour.WHITE) ? "    .   |" : "    .   |";
	}

	@Override
	public List<List<Move>> movesInRange(XY cell) {
		return new ArrayList<>();
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
