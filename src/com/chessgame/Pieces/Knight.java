package com.chessgame.Pieces;

import com.chessgame.Board.Board;
import com.chessgame.Pieces.Movement.KnightMovement;

public class Knight extends Piece {

	public Knight(int x, int y, boolean isWhite, Board board, int value) {
		super(x, y, isWhite, board, value, new KnightMovement());
	}

	@Override
	public void initializeSide(int value) {
		this.value = value;
		if (isWhite) {
			image = PieceImages.wn;
		} else {
			image = PieceImages.bn;
		}
	}
}