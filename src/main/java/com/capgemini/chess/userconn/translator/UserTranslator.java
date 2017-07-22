package com.capgemini.chess.userconn.translator;

import com.capgemini.chess.algorithms.model.location.XY;

public class UserTranslator {

	private static final int letterDist = 97;
	private static final int digitDist = 1;

	public String translateXYtoUserReadable(XY cell) {
		String firstSign = Character.valueOf((char) (cell.getX() + letterDist)).toString();
		String secondSign = String.valueOf(cell.getY() + digitDist);
		return firstSign + secondSign;
	}

	public XY translateUserInputToXY(String input) {
		int firstSignValue = input.toLowerCase().codePointAt(0);
		int secondSignValue = Integer.parseInt(input.substring(1));
		XY referencedCell = new XY(firstSignValue - letterDist, secondSignValue - digitDist);
		return referencedCell;
	}

}
