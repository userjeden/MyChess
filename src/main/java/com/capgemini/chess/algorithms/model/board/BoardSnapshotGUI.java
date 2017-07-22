package com.capgemini.chess.algorithms.model.board;

import com.capgemini.chess.algorithms.StaticValues;
import com.capgemini.chess.algorithms.model.piece.Piece;

public class BoardSnapshotGUI {
	
	private static int SIZE;
	
	protected BoardSnapshotGUI(){
		SIZE = StaticValues.BOARD_SIZE;
	}
	
	
	public String printBoard(Piece[][] board){
		
		StringBuilder strb = new StringBuilder();
		strb.append(" ________ ________ ________ ________ ________ ________ ________ ________ \n");
		
		for(int i = 0; i < SIZE; i++){
			strb.append("|        |        |        |        |        |        |        |        |\n");
			strb.append("|");
			
			for(int j = 0; j < SIZE; j++){
				strb.append(board[j][i].printPiece());
			}
			
			strb.append(" " + (8-i) + "\n");
			strb.append("|        |        |        |        |        |        |        |        |\n");
			strb.append(" ________ ________ ________ ________ ________ ________ ________ ________ \n");
		}
		
		strb.append("\n");
		strb.append("     A        B        C        D        E        F        G        H    \n");
		return strb.toString();
	}
	
	

}
