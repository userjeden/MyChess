package com.capgemini.chess.userconn.translator;

public class InputValidator {

	public boolean validateInput(String input){
		return input.matches("[a-hA-H]{1}[1-8]{1}");
	}
}
