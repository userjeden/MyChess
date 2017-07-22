package com.capgemini.chess.algorithms.model.location;

import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.model.piece.Piece;

public class Move {

	private XY sourceCell;
	private XY targetCell;
	private MoveType moveType;
	private Piece movedPiece;

	
	public Move(XY source, XY target) {
		this.sourceCell = source;
		this.targetCell = target;
	}

	public Move(Piece piece, XY source, XY target) {
		this(source, target);
		this.movedPiece = piece;
	}

	public Move(Piece piece, XY source, XY target, MoveType type) {
		this(piece, source, target);
		this.moveType = type;
	}

	
	@Override
	public String toString() {
		return "from: " + sourceCell.getX() + " " + sourceCell.getY() + 
				" to: " + targetCell.getX() + " " + targetCell.getY() + 
				" type: " + moveType;
	}

	public XY getIsoSourceCell() {
		return new XY(sourceCell.getX(), sourceCell.getY());
	}

	public void setIsoSourceCell(XY sourceCell) {
		this.sourceCell = new XY(sourceCell.getX(), sourceCell.getY());
	}

	public XY getIsoTargetCell() {
		return new XY(targetCell.getX(), targetCell.getY());
	}

	public void setIsoTargetCell(XY targetCell) {
		this.targetCell = new XY(targetCell.getX(), targetCell.getY());
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public Piece getMovedPiece() {
		return movedPiece;
	}

	public void setMovedPiece(Piece movedPiece) {
		this.movedPiece = movedPiece;
	}

}
