package com.capgemini.chess.algorithms;

import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.PieceBishop;
import com.capgemini.chess.algorithms.model.piece.PieceEmpty;
import com.capgemini.chess.algorithms.model.piece.PieceKing;
import com.capgemini.chess.algorithms.model.piece.PieceKnight;
import com.capgemini.chess.algorithms.model.piece.PiecePawn;
import com.capgemini.chess.algorithms.model.piece.PieceQueen;
import com.capgemini.chess.algorithms.model.piece.PieceRook;

public class HelperGameManager {

	private static int BOARD_SIZE;
	
	public HelperGameManager(){
		BOARD_SIZE = StaticValues.BOARD_SIZE;
	}
	
	
	public BoardSnapshot initializeGame(){
		
		BoardSnapshot board = new BoardSnapshot();
		
		for(int i = 0; i < BOARD_SIZE; i++){
			for(int j = 0; j < BOARD_SIZE; j++){
				board.setPieceAt(new PieceEmpty(PieceColour.EMPTY), new XY(i, j));
			}
		}
		
		board.setPieceAt(new PieceRook(PieceColour.WHITE), new XY(0, 0));
		board.setPieceAt(new PieceKnight(PieceColour.WHITE), new XY(1, 0));
		board.setPieceAt(new PieceBishop(PieceColour.WHITE), new XY(2, 0));
		board.setPieceAt(new PieceQueen(PieceColour.WHITE), new XY(3, 0));
		board.setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		board.setPieceAt(new PieceBishop(PieceColour.WHITE), new XY(5, 0));
		board.setPieceAt(new PieceKnight(PieceColour.WHITE), new XY(6, 0));
		board.setPieceAt(new PieceRook(PieceColour.WHITE), new XY(7, 0));
		
		for(int i = 0; i < BOARD_SIZE; i++){
			board.setPieceAt(new PiecePawn(PieceColour.WHITE), new XY(i, 1));
		}
		
		board.setPieceAt(new PieceRook(PieceColour.BLACK), new XY(0, 7));
		board.setPieceAt(new PieceKnight(PieceColour.BLACK), new XY(1, 7));
		board.setPieceAt(new PieceBishop(PieceColour.BLACK), new XY(2, 7));
		board.setPieceAt(new PieceQueen(PieceColour.BLACK), new XY(3, 7));
		board.setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		board.setPieceAt(new PieceBishop(PieceColour.BLACK), new XY(5, 7));
		board.setPieceAt(new PieceKnight(PieceColour.BLACK), new XY(6, 7));
		board.setPieceAt(new PieceRook(PieceColour.BLACK), new XY(7, 7));
		
		for(int i = 0; i < BOARD_SIZE; i++){
			board.setPieceAt(new PiecePawn(PieceColour.BLACK), new XY(i, 6));
		}
		
		return board;
	}
	

	public BoardSnapshot initializeGameEmpty(){
		
		BoardSnapshot board = new BoardSnapshot();
		
		for(int i = 0; i < BOARD_SIZE; i++){
			for(int j = 0; j < BOARD_SIZE; j++){
				board.setPieceAt(new PieceEmpty(PieceColour.EMPTY), new XY(i, j));
			}
		}
		
		return board;
	}
	
}
