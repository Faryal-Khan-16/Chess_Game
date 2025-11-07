package com.chessgame.Game;

import com.chessgame.Board.Board;
import com.chessgame.Pieces.Piece;

public class MoveValidator {
    private Board board;
    private GameStateManager stateManager;

    public MoveValidator(Board board, GameStateManager stateManager) {
        this.board = board;
        this.stateManager = stateManager;
    }

    public boolean isValidMove(Piece piece, int toX, int toY) {
        if (piece == null) return false;

        // Check if the move is in the piece's legal moves list
        for (com.chessgame.Board.Move move : piece.getMoves()) {
            if (move.getToX() == toX && move.getToY() == toY) {
                return true;
            }
        }
        return false;
    }

    public void filterLegalMoves(Piece piece) {
        if (piece == null) return;

        java.util.List<com.chessgame.Board.Move> illegalMoves = new java.util.ArrayList<>();

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
        com.chessgame.Pieces.King king = isWhite ?
                GameStateManager.getWhiteKing() : GameStateManager.getBlackKing();

        if (king == null) return false;

        // Check if any enemy piece can attack the king's position
        for (Piece p : GameStateManager.getAllPieces()) {
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

    public boolean isSquareUnderAttack(int x, int y, boolean byWhite) {
        // Check if a square is attacked by any piece of the given color
        for (Piece p : GameStateManager.getAllPieces()) {
            if (p.isWhite() == byWhite && p.isAlive()) {
                if (p.canMove(x, y, board)) {
                    return true;
                }
            }
        }
        return false;
    }
}