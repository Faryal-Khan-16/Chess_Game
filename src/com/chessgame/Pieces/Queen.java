package com.chessgame.Pieces;

import com.chessgame.Board.Board;
import com.chessgame.Pieces.Movement.QueenMovement;

public class Queen extends Piece {

	public Queen(int x, int y, boolean isWhite, Board board, int value) {
		super(x, y, isWhite, board, value, new QueenMovement());
	}

	@Override
	public void initializeSide(int value) {
		this.value = value;
		if (isWhite) {
			image = PieceImages.wq;
		} else {
			image = PieceImages.bq;
		}
	}
}