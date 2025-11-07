package com.chessgame.Pieces;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import com.chessgame.Board.Board;
import com.chessgame.Board.Move;
import com.chessgame.Board.Position;
import com.chessgame.Pieces.Movement.MovementStrategy;

public abstract class Piece implements Cloneable {
	protected Position position;
	protected boolean isWhite;
	protected boolean isAlive;
	protected int value;
	protected Board board;
	protected MovementStrategy movementStrategy;
	public static int SIZE = 80;

	protected String pieceImage;
	protected javax.swing.ImageIcon image;
	protected List<Move> moves = new ArrayList<>();

	public Piece(int x, int y, boolean isWhite, Board board, int value, MovementStrategy movementStrategy) {
		this.position = new Position(x, y);
		this.isWhite = isWhite;
		this.board = board;
		this.value = value;
		this.movementStrategy = movementStrategy;
		this.isAlive = true;
		initializeSide(value);
		board.setPieceIntoBoard(x, y, this);
	}

	public abstract void initializeSide(int value);

	public boolean makeMove(int toX, int toY, Board board) {
		if (!isAlive()) return false;

		for (Move move : moves) {
			if (move.getToX() == toX && move.getToY() == toY) {
				board.updatePieces(position.getX(), position.getY(), toX, toY, this);
				position = new Position(toX, toY);
				return true;
			}
		}
		return false;
	}

	public boolean canMove(int x, int y, Board board) {
		if (!new Position(x, y).isValid()) return false;
		return movementStrategy.isValidMove(board, this.position, new Position(x, y));
	}

	public boolean isAlive() {
		if (board.getPiece(position.getX(), position.getY()) != this) {
			isAlive = false;
		}
		return isAlive;
	}

	public void fillAllPseudoLegalMoves(Board board) {
		moves.clear();
		List<Position> possiblePositions = movementStrategy.getPossibleMoves(board, position);

		for (Position pos : possiblePositions) {
			moves.add(new Move(position.getX(), position.getY(), pos.getX(), pos.getY(), this));
		}
	}

	public void showMoves(Graphics g, JPanel panel) {
		// Implementation for showing possible moves
		// This would draw circles or highlights on possible move squares
		// For now, it's a placeholder
	}

	public void draw(Graphics g, boolean drag, JPanel panel) {
		if (image != null) {
			int drawX = position.getX() * SIZE;
			int drawY = position.getY() * SIZE;
			g.drawImage(image.getImage(), drawX, drawY, SIZE, SIZE, panel);
		}
	}

	public void draw2(Graphics g, boolean player, int x, int y, JPanel panel) {
		if (image != null) {
			g.drawImage(image.getImage(), x - SIZE/2, y - SIZE/2, SIZE, SIZE, panel);
		}
	}

	// Getters and Setters
	public int getXcord() { return position.getX(); }
	public void setXcord(int x) { this.position = new Position(x, position.getY()); }
	public int getYcord() { return position.getY(); }
	public void setYcord(int y) { this.position = new Position(position.getX(), y); }
	public boolean isWhite() { return isWhite; }
	public void setWhite(boolean white) { isWhite = white; }
	public Board getBoard() { return board; }
	public void setBoard(Board board) { this.board = board; }
	public int getValueInTheboard() { return value; }
	public void setValueInTheboard(int value) { this.value = value; }
	public List<Move> getMoves() { return new ArrayList<>(moves); }
	public void setMoves(List<Move> moves) { this.moves = new ArrayList<>(moves); }
	public Position getPosition() { return new Position(position.getX(), position.getY()); }
	public void setAlive(boolean alive) { this.isAlive = alive; }

	// Remove the @Override annotation since this is not overriding Object.clone()
	public Piece getClone() {
		try {
			Piece clone = (Piece) super.clone();
			clone.position = new Position(this.position.getX(), this.position.getY());
			clone.moves = new ArrayList<>(this.moves);
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	// If you want to override the actual clone() method, you can add this:
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Piece clone = (Piece) super.clone();
		clone.position = new Position(this.position.getX(), this.position.getY());
		clone.moves = new ArrayList<>(this.moves);
		return clone;
	}

	@Override
	public String toString() {
		return (isWhite ? "White " : "Black ") + this.getClass().getSimpleName() +
				" at (" + position.getX() + "," + position.getY() + ")";
	}
}
