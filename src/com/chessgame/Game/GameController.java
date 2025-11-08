package com.chessgame.Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import com.chessgame.Board.Board;
import com.chessgame.Pieces.Piece;
import com.chessgame.Pieces.King;

public class GameController {
    private Game game;
    private Board board;
    private GameStateManager stateManager;
    private MoveValidator moveValidator;
    private PromotionHandler promotionHandler;

    public GameController(Game game, Board board, GameStateManager stateManager,
                          MoveValidator moveValidator, PromotionHandler promotionHandler) {
        this.game = game;
        this.board = board;
        this.stateManager = stateManager;
        this.moveValidator = moveValidator;
        this.promotionHandler = promotionHandler;
    }

    public void draw(Graphics g, int x, int y, JPanel panel) {
        drawBoard(g);
        drawPieces(g, panel);
        drawPossibleMoves(g, panel);
        drawDraggedPiece(g, x, y, panel);
        drawKingInCheck(g, panel);
    }

    public void selectPiece(int x, int y) {
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return;

        Piece selected = board.getPiece(x, y);
        if (selected != null && selected.isWhite() == Game.currentPlayer) {
            game.activePiece = selected;
            // Generate and highlight legal moves
            selected.fillAllPseudoLegalMoves(board);
            moveValidator.filterLegalMoves(selected);
        } else {
            game.activePiece = null;
        }
    }

    public void move(int x, int y) {
        if (game.activePiece == null) return;
        if (x < 0 || x >= 8 || y < 0 || y >= 8) return;

        // Check if the move is valid
        if (moveValidator.isValidMove(game.activePiece, x, y)) {
            executeMove(game.activePiece, x, y);
        }

        Game.isDragging = false;
        game.activePiece = null;
    }

    private void executeMove(Piece piece, int toX, int toY) {
        int fromX = piece.getXcord();
        int fromY = piece.getYcord();

        board.updatePieces(fromX, fromY, toX, toY, piece);

        // Check for pawn promotion
        if (piece instanceof com.chessgame.Pieces.Pawn) {
            com.chessgame.Pieces.Pawn pawn = (com.chessgame.Pieces.Pawn) piece;
            if (pawn.madeToTheEnd()) {
                promotionHandler.handlePromotion(pawn);
            }
        }

        // Update game state
        stateManager.switchPlayer();
        stateManager.updateGameState();
    }

    private void drawBoard(Graphics g) {
        Color lightSquare = new Color(238, 238, 210);
        Color darkSquare = new Color(118, 150, 86);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(lightSquare);
                } else {
                    g.setColor(darkSquare);
                }
                g.fillRect(i * Piece.SIZE, j * Piece.SIZE, Piece.SIZE, Piece.SIZE);
            }
        }
    }

    private void drawPieces(Graphics g, JPanel panel) {
        // ✅ FIXED: Draw pieces from the actual board state instead of pieces list
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board.getPiece(x, y);
                if (p != null && (p != game.activePiece || !Game.isDragging)) {
                    p.draw(g, false, panel);
                }
            }
        }
    }

    private void drawPossibleMoves(Graphics g, JPanel panel) {
        if (game.activePiece != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));

            for (com.chessgame.Board.Move move : game.activePiece.getMoves()) {
                int x = move.getToX() * Piece.SIZE;
                int y = move.getToY() * Piece.SIZE;

                // Draw move indicator
                g2.setColor(new Color(0, 255, 0, 100));
                g2.fillOval(x + Piece.SIZE/4, y + Piece.SIZE/4, Piece.SIZE/2, Piece.SIZE/2);

                // Draw border
                g2.setColor(Color.GREEN);
                g2.drawOval(x + Piece.SIZE/4, y + Piece.SIZE/4, Piece.SIZE/2, Piece.SIZE/2);
            }
        }
    }

    private void drawDraggedPiece(Graphics g, int x, int y, JPanel panel) {
        if (game.activePiece != null && Game.isDragging) {
            game.activePiece.draw2(g, Game.currentPlayer, x, y, panel);
        }
    }

    private void drawKingInCheck(Graphics g, JPanel panel) {
        King kingInCheck = stateManager.getKingInCheck();
        if (kingInCheck != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(4));

            int x = kingInCheck.getXcord() * Piece.SIZE;
            int y = kingInCheck.getYcord() * Piece.SIZE;
            g2.drawRect(x, y, Piece.SIZE, Piece.SIZE);
        }
    }
}
