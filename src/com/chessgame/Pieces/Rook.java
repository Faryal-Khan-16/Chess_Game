package com.chessgame.Pieces;

import com.chessgame.Board.Board;
import com.chessgame.Pieces.Movement.RookMovement;

public class Rook extends Piece {
	private boolean hasMoved;

	public Rook(int x, int y, boolean isWhite, Board board, int value) {
		super(x, y, isWhite, board, value, new RookMovement());
		this.hasMoved = false;
	}

	@Override
	public void initializeSide(int value) {
		this.value = value;
		if (isWhite) {
			image = PieceImages.wr;
		} else {
			image = PieceImages.br;
		}
	}

	@Override
	public boolean makeMove(int toX, int toY, Board board) {
		boolean success = super.makeMove(toX, toY, board);
		if (success && !hasMoved) {
			hasMoved = true;
		}
		return success;
	}

	// Getters and Setters
	public boolean hasMoved() { return hasMoved; }
	public void setHasMoved(boolean hasMoved) { this.hasMoved = hasMoved; }
}