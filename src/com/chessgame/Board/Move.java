package com.chessgame.Board;

import com.chessgame.Pieces.Piece;

import java.util.Objects;

public class Move implements Comparable<Move> {
	private int fromX, fromY, toX, toY;
	private Piece piece;

	public Move(int fromX, int fromY, int toX, int toY, Piece piece) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
		this.piece = piece;
	}

	public int getFromX() { return fromX; }
	public int getFromY() { return fromY; }
	public int getToX() { return toX; }
	public int getToY() { return toY; }
	public Piece getPiece() { return piece; }

	public void setFromX(int fromX) { this.fromX = fromX; }
	public void setFromY(int fromY) { this.fromY = fromY; }
	public void setToX(int toX) { this.toX = toX; }
	public void setToY(int toY) { this.toY = toY; }
	public void setPiece(Piece piece) { this.piece = piece; }

	@Override
	public int compareTo(Move other) {
		if (toX == other.getToX() && toY == other.getToY() &&
				fromX == other.getFromX() && fromY == other.getFromY()) {
			return 0;
		}
		return -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Move other = (Move) obj;
		return toX == other.toX && toY == other.toY &&
				fromX == other.fromX && fromY == other.fromY &&
				piece == other.piece;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fromX, fromY, toX, toY, piece);
	}

	@Override
	public String toString() {
		String pieceName = piece != null ? piece.getClass().getSimpleName() : "Unknown";
		return pieceName + " from (" + fromX + "," + fromY + ") to (" + toX + "," + toY + ")";
	}
}