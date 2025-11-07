package com.chessgame.Game;

import javax.swing.JOptionPane;
import com.chessgame.Board.Board;
import com.chessgame.Pieces.*;

public class PromotionHandler {
    private Game game;
    private Board board;
    private GameStateManager stateManager;

    public PromotionHandler(Game game, Board board, GameStateManager stateManager) {
        this.game = game;
        this.board = board;
        this.stateManager = stateManager;
    }

    public void handlePromotion(Pawn pawn) {
        if (pawn == null || !pawn.madeToTheEnd()) return;

        int choice = showPromotionDialog();
        promotePawn(pawn, choice);
    }

    private int showPromotionDialog() {
        Object[] options = { "Queen", "Rook", "Knight", "Bishop" };
        Game.isDragging = false;

        int choice = JOptionPane.showOptionDialog(null,
                "Choose piece for pawn promotion:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        return choice;
    }

    private void promotePawn(Pawn pawn, int choice) {
        int x = pawn.getXcord();
        int y = pawn.getYcord();
        boolean isWhite = pawn.isWhite();

        // Remove the pawn from all lists
        GameStateManager.getAllPieces().remove(pawn);
        if (isWhite) {
            GameStateManager.getWhitePieces().remove(pawn);
        } else {
            GameStateManager.getBlackPieces().remove(pawn);
        }

        // Create the new piece based on choice
        Piece newPiece = createPromotionPiece(x, y, isWhite, choice);

        // Add the new piece to the board and lists
        board.setPieceIntoBoard(x, y, newPiece);
        GameStateManager.getAllPieces().add(newPiece);
        if (isWhite) {
            GameStateManager.getWhitePieces().add(newPiece);
        } else {
            GameStateManager.getBlackPieces().add(newPiece);
        }

        // Update the piece lists
        GameStateManager.fillPieces();
    }

    private Piece createPromotionPiece(int x, int y, boolean isWhite, int choice) {
        switch (choice) {
            case 0: // Queen
                return new Queen(x, y, isWhite, board, isWhite ? 9 : -9);
            case 1: // Rook
                return new Rook(x, y, isWhite, board, isWhite ? 5 : -5);
            case 2: // Knight
                return new Knight(x, y, isWhite, board, isWhite ? 3 : -3);
            case 3: // Bishop
                return new Bishop(x, y, isWhite, board, isWhite ? 3 : -3);
            default: // Default to Queen
                return new Queen(x, y, isWhite, board, isWhite ? 9 : -9);
        }
    }
}