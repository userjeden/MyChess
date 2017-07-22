package com.capgemini.chess.algorithms;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.PieceBishop;
import com.capgemini.chess.algorithms.model.piece.PieceEmpty;
import com.capgemini.chess.algorithms.model.piece.PieceKing;
import com.capgemini.chess.algorithms.model.piece.PiecePawn;
import com.capgemini.chess.algorithms.model.piece.PieceRook;


public class GameManagerMovesTest {

	
	
	@Test
	public void testMakeMovePawnRegular() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 1), new XY(5, 2));
		Move move = gameManager.getLastMove();

		// then
		assertEquals(MoveType.REGULAR, move.getMoveType());
		assertEquals(PieceType.PAWN, move.getMovedPiece().getType());
		System.out.println("TEST Pawn Regular \n" + gameManager.printBoard());		
	}

	
	@Test
	public void testMakeMoveBishopRegular() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.makeMove(gameManager.getLastBoard(), new XY(3, 1), new XY(3, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(1, 6), new XY(1, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 0), new XY(6, 4));
		Move move = gameManager.getLastMove();
		
		// then
		assertEquals(MoveType.REGULAR, move.getMoveType());
		assertEquals(PieceType.BISHOP, move.getMovedPiece().getType());
		System.out.println("TEST Bishop Regular \n" + gameManager.printBoard());
	}
	
	
	@Test
	public void testMakeMoveKingRegular() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 1), new XY(4, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 6), new XY(2, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 0), new XY(4, 1));
		Move move = gameManager.getLastMove();

		// then
		assertEquals(MoveType.REGULAR, move.getMoveType());
		assertEquals(PieceType.KING, move.getMovedPiece().getType());
		System.out.println("TEST King Regular \n" + gameManager.printBoard());			
	}
	
	
	@Test
	public void testMakeMoveKnightCapture() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.makeMove(gameManager.getLastBoard(), new XY(1, 0), new XY(2, 2));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(6, 6), new XY(6, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 2), new XY(1, 4));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 6), new XY(4, 4));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(1, 4), new XY(2, 6));
		Move move = gameManager.getLastMove();

		// then
		assertEquals(MoveType.CAPTURE, move.getMoveType());
		assertEquals(PieceType.KNIGHT, move.getMovedPiece().getType());
		System.out.println("TEST Knight Capture \n" + gameManager.printBoard());			
	}
	
	
	@Test
	public void testMakeMoveQueenCapture() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.setPieceAtLocation(new PieceEmpty(PieceColour.EMPTY), new XY(3, 1));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(3, 0), new XY(3, 6));
		Move move = gameManager.getLastMove();

		// then
		assertEquals(MoveType.CAPTURE, move.getMoveType());
		assertEquals(PieceType.QUEEN, move.getMovedPiece().getType());
		System.out.println("TEST Queen Capture \n" + gameManager.printBoard());			
	}
	
	
	@Test
	public void testMakeMoveRookCapture() 
			throws InvalidMoveException {

		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.setPieceAtLocation(new PieceEmpty(PieceColour.EMPTY), new XY(0, 1));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(0, 0), new XY(0, 6));
		Move move = gameManager.getLastMove();

		// then
		assertEquals(MoveType.CAPTURE, move.getMoveType());
		assertEquals(PieceType.ROOK, move.getMovedPiece().getType());
		System.out.println("TEST Rook Capture \n" + gameManager.printBoard());	
	}
	
	
	@Test
	public void testMakeMoveEnPassant() throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(7, 7));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(0, 7));
		gameManager.setPieceAtLocation(new PiecePawn(PieceColour.WHITE), new XY(1, 1));
		gameManager.setPieceAtLocation(new PiecePawn(PieceColour.BLACK), new XY(2, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(1, 1), new XY(1, 3));
				
		// when
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 3), new XY(1, 2));
		
		// then
		assertEquals(3, calculateNumberOfPieces(gameManager.getLastBoard()));
		System.out.println("TEST enPassant \n" + gameManager.printBoard());			
	}		
	
	
	@Test
	public void testMakeMoveCastling() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.makeMove(gameManager.getLastBoard(), new XY(1, 0), new XY(2, 2));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(1, 6), new XY(1, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(3, 1), new XY(3, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 6), new XY(2, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 0), new XY(4, 2));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(3, 6), new XY(3, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(3, 0), new XY(3, 1));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 6), new XY(4, 5));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 0), new XY(2, 0));
		Move move = gameManager.getLastMove();
		
		// then
		assertEquals(MoveType.CASTLING, move.getMoveType());
		assertEquals(PieceType.KING, move.getMovedPiece().getType());
		System.out.println("TEST Castling \n" + gameManager.printBoard());			
	}
		
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	
	@Test
	public void testMakeMoveInvalidKingWouldBeChecked() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		gameManager.getLastBoard().setPieceAt(new PieceBishop(PieceColour.WHITE), new XY(4, 1));
		gameManager.getLastBoard().setPieceAt(new PieceRook(PieceColour.BLACK), new XY(4, 5));
		
		// when
		exception.expect(KingInCheckException.class);
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 1), new XY(5, 2));
		
		// then 
		assertTrue(gameManager.getPieceAtLocation(new XY(4, 1)).getType() == PieceType.ROOK);
	}
	
	
	@Test
	public void testMakeMoveInvalidIndexOutOfBound() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		exception.expect(InvalidMoveException.class);
		gameManager.makeMove(gameManager.getLastBoard(), new XY(6, 0), new XY(8, 2));
		
		// then 
		assertTrue(gameManager.getPieceAtLocation(new XY(6, 0)).getType() == PieceType.KNIGHT );
	}
	
	
	@Test
	public void testMakeMoveInvalidMoveOrder() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		exception.expect(InvalidMoveException.class);
		gameManager.makeMove(gameManager.getLastBoard(), new XY(6, 0), new XY(6, 2));	
		
		// then 
		assertTrue(gameManager.getPieceAtLocation(new XY(6, 0)).getType() == PieceType.KNIGHT );
	}
	
	
	@Test
	public void testMakeMoveInvalidSameSpot() {
		
		// given
		boolean exceptionThrown = false;
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		gameManager.getLastBoard().setPieceAt(new PiecePawn(PieceColour.WHITE), new XY(4, 1));
		
		// when
		try {
			gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 1), new XY(4, 1));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	
	@Test
	public void testMakeMoveInvalidPawnDestination() {
		
		// given
		boolean exceptionThrown = false;
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		gameManager.getLastBoard().setPieceAt(new PiecePawn(PieceColour.WHITE), new XY(4, 1));
		
		// when
		try {
			gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 1), new XY(5, 2));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}		
		
		// then 
		assertTrue(exceptionThrown);
	}
	
	
	@Test
	public void testMakeMoveInvalidPawnBackwardMove() {
	
		// given
		boolean exceptionThrown = false;
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		gameManager.getLastBoard().setPieceAt(new PiecePawn(PieceColour.WHITE), new XY(4, 1));
		
		// when
		try {
			gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 1), new XY(5, 0));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}		
		
		// then 
		assertTrue(exceptionThrown);	
	}
	
	
	@Test
	public void testMakeMoveInvalidPawnMoveDistance() {
		
		// given
		boolean exceptionThrown = false;
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		gameManager.getLastBoard().setPieceAt(new PiecePawn(PieceColour.WHITE), new XY(4, 1));
		
		// when
		try {
			gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 1), new XY(4, 4));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}		
		
		// then 
		assertTrue(exceptionThrown);	
	}

	
	@Test
	public void testMakeMoveInvalidPawnCaptureDestination() {
		
		// given
		boolean exceptionThrown = false;
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.WHITE), new XY(4, 0));
		gameManager.getLastBoard().setPieceAt(new PieceKing(PieceColour.BLACK), new XY(4, 7));
		gameManager.getLastBoard().setPieceAt(new PiecePawn(PieceColour.WHITE), new XY(4, 1));
		gameManager.getLastBoard().setPieceAt(new PiecePawn(PieceColour.BLACK), new XY(5, 3));
		
		// when
		try {
			gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 1), new XY(5, 3));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}		
		
		// then 
		assertTrue(exceptionThrown);	
	}
	
	
	@Test
	public void testMakeMoveInvalidKingDistance() {
		
		// given
		boolean exceptionThrown = false;
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		gameManager.getLastBoard().setPieceAt(new PieceEmpty(PieceColour.EMPTY), new XY(4, 1));
		
		// when
		try {
			gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 0), new XY(4, 2));
		} catch (InvalidMoveException e) {
			exceptionThrown = true;
		}		
		
		// then 
		assertTrue(exceptionThrown);	
	}
	
	
	private int calculateNumberOfPieces(BoardSnapshot board) {
		return board.getPiecesForColourAsList(PieceColour.WHITE).size() 
				+ board.getPiecesForColourAsList(PieceColour.BLACK).size();
	}
	
	
}
