package com.capgemini.chess.algorithms;

import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.XY;

public interface ChessInterface {

	
	/*
	 * Creates initial state of the Game. The returned BoardSnapshot instance
	 * includes the representation of chess board and current state of game.
	 * 
	 * @return the initial state of the board
	 */
	public BoardSnapshot initializeGame();
	
	
	/*
	 * Creates a transition basing on the input state of the game and given
	 * coordinates of expected move on the board. Returns the state of the game 
	 * after move beeing done. Throw Invalid (in general) move exceptions. 
	 * 
	 * @param   board  input state of the chess board
	 * @param   from   point to take a piece
	 * @param   dest   point to put a piece
	 * 
	 * @return  the next state of the chess board
	 */
	public BoardSnapshot makeMove(BoardSnapshot board, XY from, XY dest) 
			throws InvalidMoveException, KingInCheckException;
	
	
	/*
	 * Prints out the current graphical representation of the chess board,
	 * which is stored after last move.
	 * 
	 * @return the String representation of the chess board
	 */
	public String printBoard();

}
