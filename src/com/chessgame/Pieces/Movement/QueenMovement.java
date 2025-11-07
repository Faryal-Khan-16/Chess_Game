package com.chessgame.Pieces.Movement;

import java.util.ArrayList;
import java.util.List;
import com.chessgame.Board.Board;
import com.chessgame.Board.Position;
import com.chessgame.Pieces.Piece;

public class QueenMovement implements MovementStrategy {

    private final BishopMovement bishopMovement = new BishopMovement();
    private final RookMovement rookMovement = new RookMovement();

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        Piece piece = board.getPiece(from.getX(), from.getY());
        if (piece == null) return false;

        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        // Queen moves like bishop OR rook
        boolean isDiagonal = Math.abs(toX - fromX) == Math.abs(toY - fromY);
        boolean isStraight = (fromX == toX || fromY == toY);

        if (!isDiagonal && !isStraight) {
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

        // Combine bishop and rook moves
        moves.addAll(bishopMovement.getPossibleMoves(board, current));
        moves.addAll(rookMovement.getPossibleMoves(board, current));

        return moves;
    }

    private boolean isPathClear(Board board, int fromX, int fromY, int toX, int toY) {
        int dx = Integer.compare(toX - fromX, 0);
        int dy = Integer.compare(toY - fromY, 0);

        // If not moving diagonally or straight, path isn't clear
        if (dx != 0 && dy != 0 && Math.abs(dx) != Math.abs(dy)) {
            return false;
        }

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