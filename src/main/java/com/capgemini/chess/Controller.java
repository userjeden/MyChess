package com.capgemini.chess;

import java.util.Scanner;

import com.capgemini.chess.algorithms.GameManager;
import com.capgemini.chess.algorithms.HelperValidateMove;
import com.capgemini.chess.algorithms.InvalidMoveException;
import com.capgemini.chess.algorithms.KingInCheckException;
import com.capgemini.chess.algorithms.enums.GameResult;
import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.userconn.translator.InputValidator;
import com.capgemini.chess.userconn.translator.UserTranslator;

public class Controller {

	private static boolean PRINT_SUGGESTIONS;
	
	public Controller(Boolean printSuggestions){
		PRINT_SUGGESTIONS = printSuggestions;
	}
	
	
	public void run(){
		
		InputValidator val = new InputValidator();
		UserTranslator trl = new UserTranslator();
		
		GameManager game = new GameManager();
		BoardSnapshot initialBoard = game.initializeGame();

		System.out.println(game.printBoard());
		System.out.println("\ncurrent game state: " + game.getGameResult());
		System.out.println("provide move for: " + game.colourForCurrentMove());
		
		if(PRINT_SUGGESTIONS){
			HelperValidateMove hvm = new HelperValidateMove(initialBoard, game.colourForCurrentMove());
			hvm.importSpecialMovesFromBoard(game.getLastMove());
			hvm.printoutPossibleMoves();
		}
		
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine() && game.getGameResult() == GameResult.ONGOING){
			
			
			//requires declaration of SOURCE
			String inputOne = scanner.nextLine();
			while(!val.validateInput(inputOne)){
				System.out.println("please specify correct SOURCE CELL");
				inputOne = scanner.nextLine();
			}
			
			XY cellSource = trl.translateUserInputToXY(inputOne);
			
			
			//requires declaration of TARGET
			String inputTwo = scanner.nextLine();
			while(!val.validateInput(inputTwo)){
				System.out.println("please specify correct TARGET CELL");
				inputTwo = scanner.nextLine();	
			}
			
			XY cellTarget = trl.translateUserInputToXY(inputTwo);
			
			
			//perform the MOVE
			BoardSnapshot nextBoard = new BoardSnapshot();
			try {
				
				nextBoard = game.makeMove(initialBoard, cellSource, cellTarget);
				
			} catch (KingInCheckException e) {
				System.out.println("KING is in CHECK !!!");
				System.out.println("provide again your input");
				continue;
				
			} catch (InvalidMoveException e) {
				System.out.println("this move is not allowed");
				System.out.println("provide again your input");
				continue;
			}
			
			initialBoard = nextBoard;
			
			System.out.println(game.printBoard());
			System.out.println("\ncurrent game state: " + game.getGameResult());
			System.out.println("provide move for: " + game.colourForCurrentMove());
			
			
			if(PRINT_SUGGESTIONS){
				HelperValidateMove hvm = new HelperValidateMove(initialBoard, game.colourForCurrentMove());
				hvm.importSpecialMovesFromBoard(game.getLastMove());
				hvm.printoutPossibleMoves();
			}
			
		}
	}
	
}
