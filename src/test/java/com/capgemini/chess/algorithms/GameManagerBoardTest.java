package com.capgemini.chess.algorithms;
import static org.junit.Assert.*;

import org.junit.Test;
import com.capgemini.chess.algorithms.enums.BoardState;
import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.Piece;
import com.capgemini.chess.algorithms.model.piece.PieceEmpty;
import com.capgemini.chess.algorithms.model.piece.PieceKing;
import com.capgemini.chess.algorithms.model.piece.PiecePawn;
import com.capgemini.chess.algorithms.model.piece.PieceQueen;
import com.capgemini.chess.algorithms.model.piece.PieceRook;


public class GameManagerBoardTest {
	
	
	
	@Test
	public void testInitializeGame() {

		// when
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// then
		assertTrue(gameManager.getPieceAtLocation(new XY(4, 0)).getType() == PieceType.KING);
		assertTrue(gameManager.getPieceAtLocation(new XY(3, 0)).getType() == PieceType.QUEEN);
		assertTrue(gameManager.getPieceAtLocation(new XY(0, 1)).getType() == PieceType.PAWN);
		assertTrue(gameManager.getPieceAtLocation(new XY(7, 0)).getType() == PieceType.ROOK);
		assertEquals(32, calculateNumberOfPieces(gameManager.getLastBoard()));
		System.out.println("TEST Board Initialize: \n" + gameManager.printBoard());
	}
	
	
	@Test
	public void testMakeMoveRegular() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		Move move = new Move(new XY(5, 1), new XY(5, 3));
		gameManager.makeMove(gameManager.getLastBoard(), move.getIsoSourceCell(), move.getIsoTargetCell());
		
		// then
		assertTrue(gameManager.getPieceAtLocation(new XY(5, 1)).getType() == PieceType.EMPTY);
		assertTrue(gameManager.getPieceAtLocation(new XY(5, 3)).getType() == PieceType.PAWN);
		assertEquals(32, calculateNumberOfPieces(gameManager.getLastBoard()));
		System.out.println("TEST Board Regular move: \n" + gameManager.printBoard());
	}
	
	
	@Test
	public void testMakeMoveCapture() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		Move move = new Move(new XY(3, 1), new XY(2, 2));
		gameManager.setPieceAtLocation(new PiecePawn(PieceColour.BLACK), new XY(2, 2));
		gameManager.makeMove(gameManager.getLastBoard(), move.getIsoSourceCell(), move.getIsoTargetCell());
		
		// then
		assertTrue(gameManager.getPieceAtLocation(new XY(3, 1)).getType() == PieceType.EMPTY);
		assertTrue(gameManager.getPieceAtLocation(new XY(2, 2)).getType() == PieceType.PAWN);
		assertEquals(32, calculateNumberOfPieces(gameManager.getLastBoard()));
		System.out.println("TEST Board Attack move \n" + gameManager.printBoard());
	}
	
	
	@Test
	public void testMakeMoveWithPromotionEffect() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGame();
		
		// when
		gameManager.setPieceAtLocation(new PieceEmpty(PieceColour.EMPTY), new XY(2, 1));
		gameManager.setPieceAtLocation(new PieceEmpty(PieceColour.EMPTY), new XY(2, 7));
		gameManager.setPieceAtLocation(new PieceEmpty(PieceColour.EMPTY), new XY(2, 6));
		gameManager.setPieceAtLocation(new PiecePawn(PieceColour.WHITE), new XY(2, 6));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 6), new XY(2, 7));
		
		// then
		assertTrue(gameManager.getLastBoard().getPieceAt(new XY(2, 7)).getType() == PieceType.QUEEN);
		System.out.println("TEST Board after Promotion \n" + gameManager.printBoard());
	}
	
	
	@Test
	public void testMakeMoveWithEnPassantEffect() throws InvalidMoveException {
		
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
		assertEquals(PieceType.PAWN, gameManager.getLastBoard().getPieceAt(new XY(1, 2)).getType());
		assertEquals(PieceType.EMPTY, gameManager.getLastBoard().getPieceAt(new XY(1, 3)).getType());
		System.out.println("TEST enPassant \n" + gameManager.printBoard());			
	}	


	@Test
	public void testMakeMoveWithCastlingEffect() 
			throws KingInCheckException, InvalidMoveException {
		
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
		
		// then
		assertEquals(PieceType.ROOK, gameManager.getLastBoard().getPieceAt(new XY(3, 0)).getType());
		assertEquals(PieceType.KING, gameManager.getLastBoard().getPieceAt(new XY(2, 0)).getType());
		System.out.println("TEST Castling \n" + gameManager.printBoard());			
	}
	
	
	@Test
	public void testUpdateBoardStateCheckMate() throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		// when
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.WHITE), new XY(0, 1));
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.WHITE), new XY(1, 0));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(4, 0));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(4, 7));
		BoardState boardState = gameManager.updateBoardState(gameManager.getLastBoard(), PieceColour.BLACK);

		// then
		assertEquals(BoardState.CHECK_MATE, boardState);
		System.out.println("TEST check mate \n" + gameManager.printBoard());		
	}
	
	
	@Test
	public void testUpdateBoardStateCheck() throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		// when
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.WHITE), new XY(1, 0));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(4, 0));
		BoardState boardState = gameManager.updateBoardState(gameManager.getLastBoard(), PieceColour.BLACK);

		// then
		assertEquals(BoardState.CHECK, boardState);
		System.out.println("TEST just check \n" + gameManager.printBoard());		
	}
	
	
	@Test
	public void testUpdateBoardStateStaleMate() throws InvalidMoveException {
	
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		// when
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(7, 0));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(6, 2));
		gameManager.setPieceAtLocation(new PieceQueen(PieceColour.WHITE), new XY(5, 1));
		BoardState boardState = gameManager.updateBoardState(gameManager.getLastBoard(), PieceColour.BLACK);

		// then
		assertEquals(BoardState.STALE_MATE, boardState);
		System.out.println("TEST stale mate \n" + gameManager.printBoard());			
	}
	
	
	@Test
	public void testUpdateBoardStateRegular() throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		// when
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.WHITE), new XY(0, 1));
		gameManager.setPieceAtLocation(new PiecePawn(PieceColour.BLACK), new XY(3, 3));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(4, 0));
		BoardState boardState = gameManager.updateBoardState(gameManager.getLastBoard(), PieceColour.BLACK);
		
		// then
		assertEquals(BoardState.REGULAR, boardState);
		System.out.println("TEST no check \n" + gameManager.printBoard());		
	}

	
	@Test
	public void testCheckThreeFoldRepetitionBrokenTrue() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(7, 7));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(0, 0));
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.WHITE), new XY(2, 2));
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.BLACK), new XY(5, 5));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 2), new XY(2, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 5), new XY(5, 6));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 3), new XY(2, 2));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 6), new XY(5, 5));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 2), new XY(2, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 5), new XY(5, 6));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 3), new XY(2, 2));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 6), new XY(5, 5));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 2), new XY(2, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 5), new XY(5, 6));
		
		// when
		boolean threeFoldRuleBroken = gameManager.checkThreeFoldRepetitionBroken();
		
		// then
		assertTrue(threeFoldRuleBroken);		
	}
	
	
	@Test
	public void testCheckThreeFoldRepetitionBrokenFalse() 
			throws InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(7, 7));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(0, 0));
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.WHITE), new XY(2, 2));
		gameManager.setPieceAtLocation(new PieceRook(PieceColour.BLACK), new XY(5, 5));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 2), new XY(2, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 5), new XY(5, 6));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 3), new XY(2, 4));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 6), new XY(5, 5));
		
		System.out.println("move done");
		gameManager.makeMove(gameManager.getLastBoard(), new XY(2, 4), new XY(2, 3));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(5, 5), new XY(5, 4));
		
		// when
		boolean threeFoldRuleBroken = gameManager.checkThreeFoldRepetitionBroken();
		
		// then
		assertFalse(threeFoldRuleBroken);		
	}
		
	
	@Test
	public void testCheckFiftyMoveRuleBroken() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(7, 7));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(0, 7));
		
		for(int i = 0; i < 100; i++){
			Move m = createGhostMove(gameManager);
			gameManager.makeMove(gameManager.getLastBoard(), m.getIsoSourceCell(), m.getIsoTargetCell());
		}
		
		// when
		boolean fiftyMovesRuleBroken = gameManager.checkFiftyMoveRuleBroken();

		// then
		assertTrue(fiftyMovesRuleBroken);	
		System.out.println("50-move rule successfull");	
	}
	
	
	@Test
	public void testCheckFiftyMoveRuleBrokenNotEnoughMoves() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(7, 7));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(0, 7));
		
		for(int i = 0; i < 99; i++){
			Move m = createGhostMove(gameManager);
			gameManager.makeMove(gameManager.getLastBoard(), m.getIsoSourceCell(), m.getIsoTargetCell());
		}
		
		// when
		boolean fiftyMovesRuleBroken = gameManager.checkFiftyMoveRuleBroken();

		// then
		assertFalse(fiftyMovesRuleBroken);	
		System.out.println("50-move rule not enough moves");	
	}
	
	
	@Test
	public void testCheckFiftyMoveRuleBrokenPawnMoved() 
			throws KingInCheckException, InvalidMoveException {
		
		// given
		GameManager gameManager = new GameManager();
		gameManager.initializeGameEmpty();
		
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.WHITE), new XY(7, 7));
		gameManager.setPieceAtLocation(new PieceKing(PieceColour.BLACK), new XY(0, 7));
		
		gameManager.setPieceAtLocation(new PiecePawn(PieceColour.WHITE), new XY(4, 4));
		gameManager.makeMove(gameManager.getLastBoard(), new XY(4, 4), new XY(4, 5));
		
		for(int i = 0; i < 99; i++){
			Move m = createGhostMove(gameManager);
			gameManager.makeMove(gameManager.getLastBoard(), m.getIsoSourceCell(), m.getIsoTargetCell());
		}
		
		// when
		boolean fiftyMovesRuleBroken = gameManager.checkFiftyMoveRuleBroken();

		// then
		assertFalse(fiftyMovesRuleBroken);		
		System.out.println("50-move rule pawn moved");	
	}

	
	private Move createGhostMove(GameManager game) {
		
		Piece piece;
		XY cellA = new XY(0, 0);
		XY cellB = new XY(0, 2);
		game.setPieceAtLocation(new PieceEmpty(PieceColour.EMPTY), cellB);
		
		if (game.colourForCurrentMove() == PieceColour.WHITE) {
			piece = new PieceRook(PieceColour.WHITE);
			game.setPieceAtLocation(piece, cellA);
			
		}else{
			piece = new PieceRook(PieceColour.BLACK);
			game.setPieceAtLocation(piece, cellA);
		}
		
		return new Move(piece, cellA, cellB, MoveType.REGULAR);
	}
	
	
	private int calculateNumberOfPieces(BoardSnapshot board) {
		return board.getPiecesForColourAsList(PieceColour.WHITE).size() 
				+ board.getPiecesForColourAsList(PieceColour.BLACK).size();
	}
}
