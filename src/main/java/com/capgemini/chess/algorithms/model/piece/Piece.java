package com.capgemini.chess.algorithms.model.piece;

import java.util.List;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;

public abstract class Piece {

	
	protected PieceType pieceType;
	protected PieceColour colour;

	public Piece(PieceColour colour, PieceType pieceType) {
		this.pieceType = pieceType;
		this.colour = colour;
	}

	public abstract List<List<Move>> movesInRange(XY cell);

	public abstract void setWasMoved();

	public abstract boolean getWasMoved();

	public abstract String printPiece();
	
	public PieceType getType(){
		return pieceType;
	}

	public PieceColour getColor() {
		return colour;
	}

}
