package com.capgemini.chess.algorithms.model.location;

import com.capgemini.chess.userconn.translator.UserTranslator;

public class XY {

	private int x;
	private int y;

	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	@Override
	public String toString(){
		UserTranslator trl = new UserTranslator();
		return trl.translateXYtoUserReadable(this);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XY other = (XY) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
