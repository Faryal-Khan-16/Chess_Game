package com.chessgame.Pieces;

import com.chessgame.Board.Board;
import com.chessgame.Game.GameStateManager;
import com.chessgame.Pieces.Movement.KingMovement;

public class King extends Piece {
	private boolean hasMoved;

	public King(int x, int y, boolean isWhite, Board board, int value) {
		super(x, y, isWhite, board, value, new KingMovement());
		this.hasMoved = false;

		// Register this king with the game state manager
		if (isWhite) {
			GameStateManager.setWhiteKing(this);
		} else {
			GameStateManager.setBlackKing(this);
		}
	}

	@Override
	public void initializeSide(int value) {
		this.value = value;
		if (isWhite) {
			image = PieceImages.wk;
		} else {
			image = PieceImages.bk;
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

	public boolean isInCheck() {
		// Check if any enemy piece can attack this king's position
		for (Piece piece : GameStateManager.getAllPieces()) {
			if (piece.isWhite() != isWhite && piece.canMove(getXcord(), getYcord(), board)) {
				return true;
			}
		}
		return false;
	}

	// Getters and Setters
	public boolean hasMoved() { return hasMoved; }
	public void setHasMoved(boolean hasMoved) { this.hasMoved = hasMoved; }
}