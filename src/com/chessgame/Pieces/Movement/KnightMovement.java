package com.chessgame.Pieces.Movement;

import java.util.ArrayList;
import java.util.List;
import com.chessgame.Board.Board;
import com.chessgame.Board.Position;
import com.chessgame.Pieces.Piece;

public class KnightMovement implements MovementStrategy {

    // All possible L-shaped moves for a knight
    private final int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    };

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        Piece piece = board.getPiece(from.getX(), from.getY());
        if (piece == null) return false;

        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        // Check if it's a valid knight move (L-shape)
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        if (!((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
            return false;
        }

        // Check if target square is empty or has enemy piece
        Piece target = board.getPiece(toX, toY);
        return target == null || target.isWhite() != piece.isWhite();
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position current) {
        List<Position> moves = new ArrayList<>();
        Piece piece = board.getPiece(current.getX(), current.getY());
        if (piece == null) return moves;

        int x = current.getX();
        int y = current.getY();

        for (int[] move : knightMoves) {
            int newX = x + move[0];
            int newY = y + move[1];
            Position newPos = new Position(newX, newY);

            if (newPos.isValid()) {
                Piece target = board.getPiece(newX, newY);
                if (target == null || target.isWhite() != piece.isWhite()) {
                    moves.add(newPos);
                }
            }
        }

        return moves;
    }
}