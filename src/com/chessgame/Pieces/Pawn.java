package com.chessgame.Pieces;

import com.chessgame.Board.Board;
import com.chessgame.Pieces.Movement.PawnMovement;

public class Pawn extends Piece {
	private boolean firstMove;
	private boolean moved2Squares;

	public Pawn(int x, int y, boolean isWhite, Board board, int value) {
		super(x, y, isWhite, board, value, new PawnMovement());
		this.firstMove = true;
		this.moved2Squares = false;
	}

	@Override
	public void initializeSide(int value) {
		this.value = value;
		if (isWhite) {
			image = PieceImages.wp;
		} else {
			image = PieceImages.bp;
		}
	}

	@Override
	public boolean makeMove(int toX, int toY, Board board) {
		boolean success = super.makeMove(toX, toY, board);
		if (success) {
			// Check if this was a double move
			if (firstMove && Math.abs(getYcord() - toY) == 2) {
				moved2Squares = true;
			} else {
				moved2Squares = false;
			}
			firstMove = false;
		}
		return success;
	}

	public boolean madeToTheEnd() {
		return (isWhite && getYcord() == 0) || (!isWhite && getYcord() == 7);
	}

	// Getters and Setters
	public boolean isFirstMove() { return firstMove; }
	public void setFirstMove(boolean firstMove) { this.firstMove = firstMove; }
	public boolean isMoved2Squares() { return moved2Squares; }
	public void setMoved2Squares(boolean moved2Squares) { this.moved2Squares = moved2Squares; }
}