package com.capgemini.chess.algorithms.enums;

public enum XYdirection {

	NORTH(0, 1), 
	SOUTH(0, -1), 
	EAST(1, 0), 
	WEST(-1, 0), 
	
	NE(1, 1), 
	NW(-1, 1), 
	SE(1, -1), 
	SW(-1, -1);
	
	
	private int deltaX;
	private int deltaY;
	
	XYdirection(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getdeltaX() {
        return deltaX;
    }
    
    public int getdeltaY() {
        return deltaY;
    }
	
}
