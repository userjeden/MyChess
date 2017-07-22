package com.capgemini.chess.userconn.translator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.capgemini.chess.algorithms.model.location.XY;

public class UserTranslatorTest {

	
	@Test
	public void testTranslateXYtoUserReadable(){
		
		// given
		XY cellOne = new XY(0, 0);
		XY cellTwo = new XY(7, 7);
		XY cellThr = new XY(1, 6);
		
		// when
		UserTranslator userTransl = new UserTranslator();
		String outOne = userTransl.translateXYtoUserReadable(cellOne);
		String outTwo = userTransl.translateXYtoUserReadable(cellTwo);
		String outThr = userTransl.translateXYtoUserReadable(cellThr);
		
		// then
		assertEquals("a1", outOne);
		assertEquals("h8", outTwo);
		assertEquals("b7", outThr);
	}
	
	
	@Test
	public void testTranslateUserInputToXY(){
		
		// given
		String inputOne = "h1";
		String inputTwo = "C3";
		String inputThr = "A8";
		
		// when
		UserTranslator userTransl = new UserTranslator();
		XY cellOne = userTransl.translateUserInputToXY(inputOne);
		XY cellTwo = userTransl.translateUserInputToXY(inputTwo);
		XY cellthr = userTransl.translateUserInputToXY(inputThr);
		
		// then
		assertEquals(new XY(7, 0), cellOne);
		assertEquals(new XY(2, 2), cellTwo);
		assertEquals(new XY(0, 7), cellthr);
	}
	
}

