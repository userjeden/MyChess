package com.capgemini.chess.algorithms.model.board;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;
import com.capgemini.chess.algorithms.GameManager;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.Piece;
import com.capgemini.chess.algorithms.model.piece.PieceBishop;
import com.capgemini.chess.algorithms.model.piece.PieceKing;
import com.capgemini.chess.algorithms.model.piece.PieceRook;

public class BoardSnapshotTest {

	
	
	@Test
	public void testGetLocationOfPieceWithColour(){
		
		// given
		GameManager gameManager = new GameManager();
		BoardSnapshot board = gameManager.initializeGameEmpty();
		Piece piece = new PieceKing(PieceColour.WHITE);
		
		// when
		board.setPieceAt(piece, new XY(3, 5));
		XY location = board.getLocationOfPieceWithColour(PieceType.KING, PieceColour.WHITE);

		// then
		assertEquals(new XY(3, 5), location);
	}
	
	
	@Test
	public void testGetPiecesForColourAsList(){
		
		// given
		GameManager gameManager = new GameManager();
		BoardSnapshot board = gameManager.initializeGameEmpty();
		board.setPieceAt(new PieceKing(PieceColour.WHITE), new XY(3, 5));
		board.setPieceAt(new PieceRook(PieceColour.WHITE), new XY(2, 2));
		board.setPieceAt(new PieceBishop(PieceColour.BLACK), new XY(6, 1));
	
		// when
		List<Piece> piecesForBlack = board.getPiecesForColourAsList(PieceColour.BLACK);
		List<Piece> piecesForWhite = board.getPiecesForColourAsList(PieceColour.WHITE);

		// then
		assertEquals(2, piecesForWhite.size());
		assertEquals(1, piecesForBlack.size());
	}
	
	
	@Test
	public void testIsCellEnemyPiece(){
		
		// given
		GameManager gameManager = new GameManager();
		BoardSnapshot board = gameManager.initializeGameEmpty();
		Piece piece = new PieceKing(PieceColour.WHITE);
		board.setPieceAt(piece, new XY(3, 5));
		
		// when
		boolean enemy = board.isCellEnemyPiece(new XY(3, 5), PieceColour.BLACK);

		// then
		assertTrue(enemy);
	}
	
	
	@Test
	public void testAreBoardsEqualWhenCloned(){
		
		// given
		GameManager gameManager = new GameManager();
		BoardSnapshot boardA = gameManager.initializeGame();
		BoardSnapshot boardB = boardA.cloneBoardSnapshot();

		// when
		boolean boardsAreEqual = boardA.areBoardsEqual(boardB);

		// then
		assertTrue(boardsAreEqual);
	}
	
	
}

