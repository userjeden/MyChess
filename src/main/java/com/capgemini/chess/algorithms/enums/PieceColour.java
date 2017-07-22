package com.capgemini.chess.algorithms.enums;

public enum PieceColour {
	
	EMPTY(0),
	WHITE(1), 
	BLACK(2);
	
	private int numValue;
	
	PieceColour(int numVal) {
        this.numValue = numVal;
    }

    public int getNumVal() {
        return numValue;
    }
	
	
}
