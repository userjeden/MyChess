package com.capgemini.chess.userconn.translator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class InputValidatorTest {
	
	
	@Test
	public void testValidateInputTRUE(){
		
		// given
		String one = "a4";
		String two = "C7";
		String thr = "h8";
		
		// when
		InputValidator inVal = new InputValidator();
		boolean bOne = inVal.validateInput(one);
		boolean bTwo = inVal.validateInput(two);
		boolean bThr = inVal.validateInput(thr);
		
		// then
		assertEquals(true, bOne);
		assertEquals(true, bTwo);
		assertEquals(true, bThr);
	}
	
	
	@Test
	public void testValidateInputFALSE(){
		
		// given
		String one = "a0";
		String two = "Ca";
		String thr = "h9";
		
		// when
		InputValidator inVal = new InputValidator();
		boolean bOne = inVal.validateInput(one);
		boolean bTwo = inVal.validateInput(two);
		boolean bThr = inVal.validateInput(thr);
		
		// then
		assertEquals(false, bOne);
		assertEquals(false, bTwo);
		assertEquals(false, bThr);
	}
	

}


