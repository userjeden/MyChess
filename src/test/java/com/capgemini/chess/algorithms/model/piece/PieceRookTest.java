package com.capgemini.chess.algorithms.model.piece;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;

public class PieceRookTest {
	
	
	@Test
	public void testMovesinRangeGenerate(){
		
		// given
		Piece rook = new PieceRook(PieceColour.WHITE);
		XY startCell = new XY(2, 2);
		
		// when
		List<List<Move>> paths = rook.movesInRange(startCell);

		// then
		int pathALength = paths.get(0).size();
		int pathBLength = paths.get(1).size();
		int pathCLength = paths.get(2).size();
		int pathDLength = paths.get(3).size();
		assertEquals(14, pathALength + pathBLength + pathCLength + pathDLength);
	}

}
