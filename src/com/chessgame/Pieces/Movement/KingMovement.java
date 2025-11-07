package com.chessgame.Pieces.Movement;

import java.util.ArrayList;
import java.util.List;
import com.chessgame.Board.Board;
import com.chessgame.Board.Position;
import com.chessgame.Pieces.King;
import com.chessgame.Pieces.Piece;
import com.chessgame.Pieces.Rook;

public class KingMovement implements MovementStrategy {

    // All possible king moves (one square in any direction)
    private final int[][] kingMoves = {
            {1, 0}, {-1, 0}, {0, 1}, {0, -1},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
    };

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        Piece piece = board.getPiece(from.getX(), from.getY());
        if (!(piece instanceof King)) return false;

        King king = (King) piece;
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        // Check castling
        if (isCastlingMove(king, fromX, fromY, toX, toY, board)) {
            return true;
        }

        // Regular king move (one square)
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        if (dx > 1 || dy > 1) {
            return false;
        }

        // Check if target square is empty or has enemy piece
        Piece target = board.getPiece(toX, toY);
        return target == null || target.isWhite() != king.isWhite();
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position current) {
        List<Position> moves = new ArrayList<>();
        Piece piece = board.getPiece(current.getX(), current.getY());
        if (!(piece instanceof King)) return moves;

        King king = (King) piece;
        int x = current.getX();
        int y = current.getY();

        // Regular moves
        for (int[] move : kingMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            Position newPos = new Position(newX, newY);

            if (newPos.isValid()) {
                Piece target = board.getPiece(newX, newY);
                if (target == null || target.isWhite() != king.isWhite()) {
                    moves.add(newPos);
                }
            }
        }

        // Castling moves
        if (!king.hasMoved()) {
            // Kingside castling
            if (canCastleKingside(king, board)) {
                moves.add(new Position(x + 2, y));
            }
            // Queenside castling
            if (canCastleQueenside(king, board)) {
                moves.add(new Position(x - 2, y));
            }
        }

        return moves;
    }

    private boolean isCastlingMove(King king, int fromX, int fromY, int toX, int toY, Board board) {
        if (king.hasMoved()) return false;

        // Must be moving horizontally 2 squares
        if (fromY != toY || Math.abs(toX - fromX) != 2) {
            return false;
        }

        if (toX > fromX) {
            return canCastleKingside(king, board);
        } else {
            return canCastleQueenside(king, board);
        }
    }

    private boolean canCastleKingside(King king, Board board) {
        int x = king.getXcord();
        int y = king.getYcord();

        // Check if kingside rook exists and hasn't moved
        Piece rook = board.getPiece(7, y);
        if (!(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false;
        }

        // Check if squares between are empty
        return board.getPiece(x + 1, y) == null && board.getPiece(x + 2, y) == null;
    }

    private boolean canCastleQueenside(King king, Board board) {
        int x = king.getXcord();
        int y = king.getYcord();

        // Check if queenside rook exists and hasn't moved
        Piece rook = board.getPiece(0, y);
        if (!(rook instanceof Rook) || ((Rook) rook).hasMoved()) {
            return false;
        }

        // Check if squares between are empty
        return board.getPiece(x - 1, y) == null && board.getPiece(x - 2, y) == null &&
                board.getPiece(x - 3, y) == null;
    }
}