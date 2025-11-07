package com.chessgame.Pieces.Movement;

import java.util.ArrayList;
import java.util.List;
import com.chessgame.Board.Board;
import com.chessgame.Board.Position;
import com.chessgame.Pieces.Piece;

public class BishopMovement implements MovementStrategy {

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        Piece piece = board.getPiece(from.getX(), from.getY());
        if (piece == null) return false;

        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        // Bishop moves diagonally
        if (Math.abs(toX - fromX) != Math.abs(toY - fromY)) {
            return false;
        }

        // Check if path is clear
        if (!isPathClear(board, fromX, fromY, toX, toY)) {
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

        // Check all four diagonal directions
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newX = x + dir[0] * i;
                int newY = y + dir[1] * i;
                Position newPos = new Position(newX, newY);

                if (!newPos.isValid()) break;

                Piece target = board.getPiece(newX, newY);
                if (target == null) {
                    moves.add(newPos);
                } else {
                    if (target.isWhite() != piece.isWhite()) {
                        moves.add(newPos);
                    }
                    break;
                }
            }
        }

        return moves;
    }

    private boolean isPathClear(Board board, int fromX, int fromY, int toX, int toY) {
        int dx = Integer.compare(toX - fromX, 0);
        int dy = Integer.compare(toY - fromY, 0);

        int currentX = fromX + dx;
        int currentY = fromY + dy;

        while (currentX != toX || currentY != toY) {
            if (board.getPiece(currentX, currentY) != null) {
                return false;
            }
            currentX += dx;
            currentY += dy;
        }

        return true;
    }
}