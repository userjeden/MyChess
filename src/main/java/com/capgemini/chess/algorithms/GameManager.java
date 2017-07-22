package com.capgemini.chess.algorithms;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.chess.algorithms.enums.BoardState;
import com.capgemini.chess.algorithms.enums.GameResult;
import com.capgemini.chess.algorithms.enums.MoveType;
import com.capgemini.chess.algorithms.enums.PieceColour;
import com.capgemini.chess.algorithms.enums.PieceType;
import com.capgemini.chess.algorithms.model.board.BoardSnapshot;
import com.capgemini.chess.algorithms.model.location.Move;
import com.capgemini.chess.algorithms.model.location.XY;
import com.capgemini.chess.algorithms.model.piece.Piece;
import com.capgemini.chess.algorithms.model.piece.PieceEmpty;
import com.capgemini.chess.algorithms.model.piece.PieceQueen;


/**
 * Game representation. 
 * Stores the game history and moves history.
 */
public class GameManager implements ChessInterface {
	
	
	private LinkedList<BoardSnapshot> boardHistory;
	private LinkedList<Move> moveHistory;
	
	private PieceColour currentMoveColour;
	private GameResult gameResult;

	
	public GameManager(){
		boardHistory = new LinkedList<>();
		moveHistory = new LinkedList<>();
		currentMoveColour = PieceColour.WHITE;
		gameResult = GameResult.ONGOING;
	}
	
	
	@Override
	public BoardSnapshot initializeGame(){
		HelperGameManager hgm = new HelperGameManager();
		BoardSnapshot board = hgm.initializeGame();
		boardHistory.add(board);
		return board;
	}

	
	@Override
	public String printBoard() {
		return boardHistory.getLast().printBoard();
	}
	
	
	@Override
	public BoardSnapshot makeMove(BoardSnapshot inputBoard, XY from, XY dest) 
			throws InvalidMoveException, KingInCheckException{
		
		
		currentMoveColour = colourForCurrentMove();
		HelperValidateMove hval = new HelperValidateMove(inputBoard, currentMoveColour);
		hval.importSpecialMovesFromBoard(getLastMove());
		
		Move move = hval.isSuchMoveAvailable(from, dest);
		if(move == null){
			throw new InvalidMoveException();
		}
		
		BoardSnapshot tempBoardAfterMove = performMove(inputBoard, move);
		BoardState stateOfTempBoard = updateBoardState(tempBoardAfterMove, colorForNextMove());
		tempBoardAfterMove.setBoardState(stateOfTempBoard);
		
		if(isKingInCheck(tempBoardAfterMove, currentMoveColour)){
			throw new KingInCheckException();
		}
		
		if(tempBoardAfterMove.getBoardState() == BoardState.STALE_MATE){
			gameResult = GameResult.DRAW;
		}
		
		if(tempBoardAfterMove.getBoardState() == BoardState.CHECK_MATE){
			gameResult = GameResult.WON;
		}
		
		move.getMovedPiece().setWasMoved();
		boardHistory.add(tempBoardAfterMove);
		moveHistory.add(move);
		return tempBoardAfterMove;	
	}
	
	
	private BoardSnapshot performMove(BoardSnapshot inputBoard, Move move) 
			throws KingInCheckException{
		
		XY from = move.getIsoSourceCell();
		XY dest = move.getIsoTargetCell();
	
		BoardSnapshot outputBoard = inputBoard.cloneBoardSnapshot();
		outputBoard.setPieceAt(outputBoard.getPieceAt(from), dest);
		outputBoard.setPieceAt(new PieceEmpty(PieceColour.EMPTY), from);	
		
		if(move.getMoveType() == MoveType.CASTLING){
			addCastlingEffect(outputBoard, move);
		}
		
		if(move.getMoveType() == MoveType.EN_PASSANT_RESPONSE){
			addEnPassantEffect(outputBoard, move);
		}
		
		if(move.getMovedPiece().getType() == PieceType.PAWN){
			addPromotionEffect(outputBoard, move);
		}
		
		return outputBoard;
	}
	

	private void addEnPassantEffect(BoardSnapshot board, Move move) {
		XY cellForRemoval = new XY(move.getIsoTargetCell().getX(), move.getIsoSourceCell().getY());
		board.setPieceAt(new PieceEmpty(PieceColour.EMPTY), cellForRemoval);
	}
	
	
	private void addPromotionEffect(BoardSnapshot board, Move move) {
		int size = StaticValues.BOARD_SIZE;
		if(move.getIsoTargetCell().getY() == size-1 && move.getMovedPiece().getColor() == PieceColour.WHITE){
			board.setPieceAt(new PieceQueen(PieceColour.WHITE), move.getIsoTargetCell());
		}
		if(move.getIsoTargetCell().getY() == 0 && move.getMovedPiece().getColor() == PieceColour.BLACK){
			board.setPieceAt(new PieceQueen(PieceColour.BLACK), move.getIsoTargetCell());
		}
	}
	
	private void addCastlingEffect(BoardSnapshot board, Move move) {
		int pivotPoint = StaticValues.BOARD_SIZE / 2;
		int castlingRow = move.getIsoTargetCell().getY();
		if(move.getIsoTargetCell().getX() < pivotPoint){
			board.setPieceAt(board.getPieceAt(new XY (0, castlingRow)), new XY (3, castlingRow));
			board.setPieceAt(new PieceEmpty(PieceColour.EMPTY), new XY (0, castlingRow));
		}else{
			board.setPieceAt(board.getPieceAt(new XY (7, castlingRow)), new XY (5, castlingRow));
			board.setPieceAt(new PieceEmpty(PieceColour.EMPTY), new XY (7, castlingRow));
		}
	}
	
	private boolean isKingInCheck(BoardSnapshot board, PieceColour colour) {
		HelperValidateMove hval = new HelperValidateMove(board, colourOppositeTo(colour));
		XY kingLocation = board.getLocationOfPieceWithColour(PieceType.KING, colour);
		if(kingLocation != null){
			return hval.isPositionUnderAttack(kingLocation);
		}
		return true;
	}
	
	private boolean isAnyMoveValid(BoardSnapshot board, PieceColour colour){
		HelperValidateMove hval = new HelperValidateMove(board, colour);
		List<Move> potentialMoves = hval.getPotentialMoves();
		for(Move m : potentialMoves){
			BoardSnapshot tempBoard = board.cloneBoardSnapshot();
			tempBoard.setPieceAt(tempBoard.getPieceAt(m.getIsoSourceCell()), m.getIsoTargetCell());
			tempBoard.setPieceAt(new PieceEmpty(PieceColour.EMPTY), m.getIsoSourceCell());
			if(!isKingInCheck(tempBoard, colour)){
				return true;
			}
		}
		return false;
	}
	
	
	public BoardState updateBoardState(BoardSnapshot board, PieceColour colour) {
		boolean isKingInCheck = isKingInCheck(board, colour);
		boolean isAnyMoveValid = isAnyMoveValid(board, colour);
		
		BoardState boardState;
		if (isKingInCheck) {
			
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
			
		} else {
			
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		return boardState;
	}
	
	
	public boolean checkFiftyMoveRuleBroken(){
		int histSize = moveHistory.size();
		if(histSize < 2*50){
			return false;
		}
		
		for(int i = histSize-1; i >= histSize-100; i--){
			Move move = moveHistory.get(i);
			if(move.getMoveType() == MoveType.CAPTURE || move.getMovedPiece().getType() == PieceType.PAWN){
				return false;
			}
		}
		return true;
	}
	
	
	public boolean checkThreeFoldRepetitionBroken(){
		
		int lastNonAtackMoveIndex = findLastNonRegularMoveIndex();
		List<BoardSnapshot> inspectedBoards = boardHistory.subList(lastNonAtackMoveIndex, boardHistory.size()-1);
		System.out.println("last non regular: " + lastNonAtackMoveIndex);
		System.out.println("inspect size: " + inspectedBoards.size());
		
		int maxRepetitionCount = 0;
		BoardSnapshot boardA = getLastBoard();
		
		for(int i = 0; i < inspectedBoards.size(); i++){
			
			BoardSnapshot boardB = inspectedBoards.get(i);
			
			if(boardA.areBoardsEqual(boardB)){
				maxRepetitionCount++;
				System.out.println("repet count: " + maxRepetitionCount + " " + i);
			}
		}
		return maxRepetitionCount >= 2;
	}

	
	private int findLastNonRegularMoveIndex() {
		int index = moveHistory.size()-1;
		for(int i = moveHistory.size()-1; i >= 0; i--){
			if(moveHistory.get(i).getMoveType() == MoveType.REGULAR){
				index = i;
			}else{
				return index;
			}
		}
		return index;
	}

	
	public PieceColour colourOppositeTo(PieceColour colour) {
		return (colour == PieceColour.WHITE) ? PieceColour.BLACK : PieceColour.WHITE;
	}
	
	public PieceColour colorForNextMove() {
		return (currentMoveColour == PieceColour.WHITE) ? 
				PieceColour.BLACK : PieceColour.WHITE;
	}
	
	public PieceColour colourForCurrentMove() {
		if (boardHistory.size() % 2 == 1) {
			return PieceColour.WHITE;
			
		} else {
			return PieceColour.BLACK;
		}
	}
	
	public Move getLastMove(){
		return (moveHistory.size() != 0) ? 
				moveHistory.getLast() : new Move(new PieceEmpty(PieceColour.EMPTY), 
						new XY(0,0), new XY(0,0), MoveType.UNDECIDED);
	}

	public BoardSnapshot getLastBoard(){
		return boardHistory.getLast();
	}
	
	public Piece getPieceAtLocation(XY cell){
		return getLastBoard().getPieceAt(cell);
	}
	
	public void setPieceAtLocation(Piece piece, XY cell){
		getLastBoard().setPieceAt(piece, cell);
	}
	
	public BoardSnapshot initializeGameEmpty(){
		HelperGameManager hgm = new HelperGameManager();
		BoardSnapshot board = hgm.initializeGameEmpty();
		boardHistory.add(board);
		return board;
	}
	
	public GameResult getGameResult(){
		return gameResult;
	}
	
}

