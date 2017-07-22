package com.capgemini.chess.algorithms.model.location;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.XYdirection;
import com.capgemini.chess.algorithms.model.piece.Piece;
import com.capgemini.chess.algorithms.model.piece.PieceBishop;

public class MoveGeneratorTest {

	
	@Test
	public void testSingleMoveGeneration(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XY from = new XY(3, 3);
		XY dest = new XY(5, 5);
		
		// when
		Move move = moveGenerator.singleMove(piece, from, dest, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(5, 5), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testSingleMoveGenerationNull(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XY from = new XY(3, 3);
		XY dest = new XY(9, 9);
		
		// when
		Move move = moveGenerator.singleMove(piece, from, dest, type);
		
		// then
		assertEquals(null, move);
	}
	
	
	@Test
	public void testNextMoveInDirectionNORTH(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.NORTH;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(3, 4), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testNextMoveInDirectionSOUTH(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.SOUTH;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(3, 2), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testNextMoveInDirectionEAST(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.EAST;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(4, 3), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testNextMoveInDirectionWEST(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.WEST;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(2, 3), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testNextMoveInDirectionNW(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.NW;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(2, 4), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testNextMoveInDirectionNE(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.NE;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(4, 4), move.getIsoTargetCell());
	}
	

	@Test
	public void testNextMoveInDirectionSE(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.SE;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(4, 2), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testNextMoveInDirectionSW(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.SW;
		XY from = new XY(3, 3);
		
		// when
		Move move = moveGenerator.nextMoveInDirection(piece, from, dir, type);
		
		// then
		assertEquals(new XY(3, 3), move.getIsoSourceCell());
		assertEquals(new XY(2, 2), move.getIsoTargetCell());
	}
	
	
	@Test
	public void testListOfMovesInDirection(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.NE;
		XY from = new XY(3, 3);
		
		// when
		List<Move> moves = moveGenerator.listOfMovesInDirect(piece, from, dir, type);
		
		// then
		assertEquals(new XY(4, 4), moves.get(0).getIsoTargetCell());
		assertEquals(new XY(7, 7), moves.get(3).getIsoTargetCell());
	}
	
	
	@Test
	public void testLimitedListOfMovesInDirection(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		XYdirection dir = XYdirection.NE;
		XY from = new XY(3, 3);
		
		// when
		List<Move> moves = moveGenerator.limListOfMovesInDirect(piece, from, dir, type, 3);
		
		// then
		assertEquals(new XY(4, 4), moves.get(0).getIsoTargetCell());
		assertEquals(3, moves.size());
	}
	
	
	@Test
	public void testListOfMovesFromList(){
		
		// given
		MoveGenerator moveGenerator = new MoveGenerator();
		Piece piece = new PieceBishop(PieceColour.WHITE);
		MoveType type = MoveType.REGULAR;
		int[][] input = {{4,5}, {4,7}, {8,8}};
		XY from = new XY(6, 7);
		
		// when
		List<List<Move>> moves = moveGenerator.listOfTracksFromList(piece, from, input, type);
		
		// then
		assertEquals(new XY(4, 5), moves.get(0).get(0).getIsoTargetCell());
		assertEquals(Collections.EMPTY_LIST, moves.get(2));
	}
	
	
}

