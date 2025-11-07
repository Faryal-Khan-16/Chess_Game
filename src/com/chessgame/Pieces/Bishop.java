package com.chessgame.Pieces;

import com.chessgame.Board.Board;
import com.chessgame.Pieces.Movement.BishopMovement;

public class Bishop extends Piece {

	public Bishop(int x, int y, boolean isWhite, Board board, int value) {
		super(x, y, isWhite, board, value, new BishopMovement());
	}

	@Override
	public void initializeSide(int value) {
		this.value = value;
		if (isWhite) {
			image = PieceImages.wb;
		} else {
			image = PieceImages.bb;
		}
	}
}