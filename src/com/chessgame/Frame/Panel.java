package com.chessgame.Frame;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import com.chessgame.Game.Game;
import com.chessgame.Pieces.Piece;

public class Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Game game;
	private int tempX, tempY;
	public static int mouseX, mouseY;

	public Panel() {
		initializePanel();
	}

	private void initializePanel() {
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		this.addMouseListener(new ChessMouseListener());
		this.addMouseMotionListener(new ChessMouseListener());
		this.addKeyListener(new ChessKeyListener());
		this.game = new Game();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (game != null) {
			game.draw(g, mouseX, mouseY, this);
		}
	}

	private class ChessMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				int x = e.getX() / Piece.SIZE;
				int y = e.getY() / Piece.SIZE;
				Game.isDragging = false;
				game.selectPiece(x, y);
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			tempX = e.getX() / Piece.SIZE;
			tempY = e.getY() / Piece.SIZE;
			if (game.getBoard().getPiece(tempX, tempY) != null) {
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			} else {
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				Game.isDragging = true;
				mouseX = e.getX();
				mouseY = e.getY();
				int boardX = e.getX() / Piece.SIZE;
				int boardY = e.getY() / Piece.SIZE;
				game.selectPiece(boardX, boardY);
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				int x = e.getX() / Piece.SIZE;
				int y = e.getY() / Piece.SIZE;
				game.move(x, y);
				repaint();
			}
		}
	}

	private class ChessKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				game.getBoard().undoMove();
				repaint();
			}
		}
	}

	public Game getGame() {
		return game;
	}
}