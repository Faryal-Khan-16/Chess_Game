package com.chessgame.Game;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import com.chessgame.Board.Board;
import com.chessgame.Pieces.*;
import com.chessgame.Frame.Home;

public class GameStateManager {
    private Game game;
    private Board board;

    // Game state
    private static King whiteKing;
    private static King blackKing;
    private static ArrayList<Piece> whitePieces = new ArrayList<>();
    private static ArrayList<Piece> blackPieces = new ArrayList<>();
    private static ArrayList<Piece> allPieces = new ArrayList<>();

    private boolean gameOver = false;

    public GameStateManager(Game game, Board board) {
        this.game = game;
        this.board = board;
    }

    public void loadStartingPosition() {
        loadFenPosition("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }

    public void initializeGameState() {
        fillPieces();
        generateAllMoves();
        validateAllMoves();
    }

    public void switchPlayer() {
        Game.switchPlayer();
        generateAllMoves();
        validateAllMoves();
        checkGameEndConditions();
    }

    public void updateGameState() {
        generateAllMoves();
        validateAllMoves();
        checkGameEndConditions();
    }

    private void generateAllMoves() {
        for (Piece p : allPieces) {
            p.fillAllPseudoLegalMoves(board);
        }
    }

    private void validateAllMoves() {
        List<Piece> currentPlayerPieces = Game.currentPlayer ? whitePieces : blackPieces;
        for (Piece p : currentPlayerPieces) {
            filterLegalMoves(p);
        }
    }

    private void filterLegalMoves(Piece piece) {
        List<com.chessgame.Board.Move> illegalMoves = new ArrayList<>();

        for (com.chessgame.Board.Move move : piece.getMoves()) {
            if (!isMoveLegal(piece, move)) {
                illegalMoves.add(move);
            }
        }

        // Remove illegal moves
        piece.getMoves().removeAll(illegalMoves);
    }

    private boolean isMoveLegal(Piece piece, com.chessgame.Board.Move move) {
        // Create a copy of the board to simulate the move
        Board testBoard = board.getNewBoard();
        Piece testPiece = testBoard.getPiece(piece.getXcord(), piece.getYcord());

        if (testPiece == null) return false;

        // Simulate the move
        testBoard.updatePieces(move.getFromX(), move.getFromY(),
                move.getToX(), move.getToY(), testPiece);

        // Check if the king would be in check after this move
        return !wouldKingBeInCheck(testBoard, piece.isWhite());
    }

    private boolean wouldKingBeInCheck(Board testBoard, boolean isWhite) {
        King king = isWhite ? whiteKing : blackKing;
        if (king == null) return false;

        // Check if any enemy piece can attack the king's position
        for (Piece p : allPieces) {
            if (p.isWhite() != isWhite && p.isAlive()) {
                // Get the test version of this piece
                Piece testPiece = testBoard.getPiece(p.getXcord(), p.getYcord());
                if (testPiece != null && testPiece.canMove(king.getXcord(), king.getYcord(), testBoard)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkGameEndConditions() {
        if (isCheckmate()) {
            handleCheckmate();
        } else if (isStalemate()) {
            handleStalemate();
        }
    }

    private boolean isCheckmate() {
        List<Piece> currentPlayerPieces = Game.currentPlayer ? whitePieces : blackPieces;
        King currentKing = Game.currentPlayer ? whiteKing : blackKing;

        if (currentKing == null) return false;

        // If king is in check and no legal moves available
        if (currentKing.isInCheck()) {
            for (Piece p : currentPlayerPieces) {
                if (!p.getMoves().isEmpty()) {
                    return false; // There are legal moves
                }
            }
            return true; // No legal moves and king is in check
        }
        return false;
    }

    private boolean isStalemate() {
        List<Piece> currentPlayerPieces = Game.currentPlayer ? whitePieces : blackPieces;
        King currentKing = Game.currentPlayer ? whiteKing : blackKing;

        if (currentKing == null) return false;

        // If king is not in check but no legal moves available
        if (!currentKing.isInCheck()) {
            for (Piece p : currentPlayerPieces) {
                if (!p.getMoves().isEmpty()) {
                    return false; // There are legal moves
                }
            }
            return true; // No legal moves but king is not in check
        }
        return false;
    }

    private void handleCheckmate() {
        String winner = Game.currentPlayer ? "Black" : "White";
        JOptionPane.showMessageDialog(null, "Checkmate! " + winner + " wins!");
        gameOver = true;
        returnToHome();
    }

    private void handleStalemate() {
        JOptionPane.showMessageDialog(null, "Stalemate! The game is a draw.");
        gameOver = true;
        returnToHome();
    }

    private void returnToHome() {
        int option = JOptionPane.showConfirmDialog(null,
                "Return to main menu?", "Game Over",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            new Home().setVisible(true);
            // Close the current game window
            if (game.getGameController() != null) {
                // You might need to get a reference to the parent frame here
            }
        }
    }

    public void loadFenPosition(String fenString) {
        // Clear existing pieces
        allPieces.clear();
        whitePieces.clear();
        blackPieces.clear();
        whiteKing = null;
        blackKing = null;

        String[] parts = fenString.split(" ");
        String position = parts[0];
        int row = 0, col = 0;

        for (char c : position.toCharArray()) {
            if (c == '/') {
                row++;
                col = 0;
            } else if (Character.isLetter(c)) {
                boolean isWhite = Character.isUpperCase(c);
                addPieceToBoard(col, row, Character.toUpperCase(c), isWhite);
                col++;
            } else if (Character.isDigit(c)) {
                col += Character.getNumericValue(c);
            }
        }

        Game.currentPlayer = parts[1].equals("w");
    }

    private void addPieceToBoard(int x, int y, char pieceType, boolean isWhite) {
        switch (pieceType) {
            case 'R':
                allPieces.add(new Rook(x, y, isWhite, board, isWhite ? 5 : -5));
                break;
            case 'N':
                allPieces.add(new Knight(x, y, isWhite, board, isWhite ? 3 : -3));
                break;
            case 'B':
                allPieces.add(new Bishop(x, y, isWhite, board, isWhite ? 3 : -3));
                break;
            case 'Q':
                allPieces.add(new Queen(x, y, isWhite, board, isWhite ? 9 : -9));
                break;
            case 'K':
                King king = new King(x, y, isWhite, board, isWhite ? 10 : -10);
                allPieces.add(king);
                if (isWhite) {
                    whiteKing = king;
                } else {
                    blackKing = king;
                }
                break;
            case 'P':
                allPieces.add(new Pawn(x, y, isWhite, board, isWhite ? 1 : -1));
                break;
        }
    }

    public static void fillPieces() {
        whitePieces.clear();
        blackPieces.clear();

        for (Piece p : allPieces) {
            if (p.isWhite()) {
                whitePieces.add(p);
            } else {
                blackPieces.add(p);
            }
        }
    }

    // Getters
    public static ArrayList<Piece> getAllPieces() { return new ArrayList<>(allPieces); }
    public static ArrayList<Piece> getWhitePieces() { return new ArrayList<>(whitePieces); }
    public static ArrayList<Piece> getBlackPieces() { return new ArrayList<>(blackPieces); }
    public static King getWhiteKing() { return whiteKing; }
    public static King getBlackKing() { return blackKing; }
    public boolean isGameOver() { return gameOver; }

    public King getKingInCheck() {
        King whiteKing = getWhiteKing();
        King blackKing = getBlackKing();

        if (whiteKing != null && whiteKing.isInCheck()) return whiteKing;
        if (blackKing != null && blackKing.isInCheck()) return blackKing;
        return null;
    }

    // Setters for kings
    public static void setWhiteKing(King king) { whiteKing = king; }
    public static void setBlackKing(King king) { blackKing = king; }
}