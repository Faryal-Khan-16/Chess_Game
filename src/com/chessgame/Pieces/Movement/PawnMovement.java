package com.chessgame.Pieces.Movement;

import java.util.ArrayList;
import java.util.List;
import com.chessgame.Board.Board;
import com.chessgame.Board.Position;
import com.chessgame.Pieces.Pawn;
import com.chessgame.Pieces.Piece;

public class PawnMovement implements MovementStrategy {

    @Override
    public boolean isValidMove(Board board, Position from, Position to) {
        Piece piece = board.getPiece(from.getX(), from.getY());
        if (!(piece instanceof Pawn)) return false;

        Pawn pawn = (Pawn) piece;
        int fromX = from.getX();
        int fromY = from.getY();
        int toX = to.getX();
        int toY = to.getY();

        // Can't move to same position
        if (fromX == toX && fromY == toY) return false;

        int direction = pawn.isWhite() ? -1 : 1;
        int startRow = pawn.isWhite() ? 6 : 1;

        // Forward move
        if (fromX == toX) {
            // Single move forward
            if (toY == fromY + direction && board.getPiece(toX, toY) == null) {
                return true;
            }
            // Double move from starting position
            if (pawn.isFirstMove() && fromY == startRow && toY == fromY + 2 * direction &&
                    board.getPiece(toX, toY) == null &&
                    board.getPiece(toX, fromY + direction) == null) {
                return true;
            }
        }

        // Capture move (diagonal)
        if (Math.abs(toX - fromX) == 1 && toY == fromY + direction) {
            Piece target = board.getPiece(toX, toY);
            if (target != null && target.isWhite() != pawn.isWhite()) {
                return true;
            }
            // En passant logic would go here
        }

        return false;
    }

    @Override
    public List<Position> getPossibleMoves(Board board, Position current) {
        List<Position> moves = new ArrayList<>();
        Piece piece = board.getPiece(current.getX(), current.getY());
        if (!(piece instanceof Pawn)) return moves;

        Pawn pawn = (Pawn) piece;
        int x = current.getX();
        int y = current.getY();
        int direction = pawn.isWhite() ? -1 : 1;

        // Forward moves
        Position forward = new Position(x, y + direction);
        if (forward.isValid() && board.getPiece(forward.getX(), forward.getY()) == null) {
            moves.add(forward);

            // Double move from starting position
            if (pawn.isFirstMove()) {
                Position doubleForward = new Position(x, y + 2 * direction);
                if (doubleForward.isValid() && board.getPiece(doubleForward.getX(), doubleForward.getY()) == null) {
                    moves.add(doubleForward);
                }
            }
        }

        // Capture moves
        int[] captureX = {x - 1, x + 1};
        for (int capture : captureX) {
            Position capturePos = new Position(capture, y + direction);
            if (capturePos.isValid()) {
                Piece target = board.getPiece(capture, y + direction);
                if (target != null && target.isWhite() != pawn.isWhite()) {
                    moves.add(capturePos);
                }
            }
        }

        return moves;
    }
}