package com.chessgame.Pieces.Movement;

import java.util.List;
import com.chessgame.Board.Board;
import com.chessgame.Board.Position;

public interface MovementStrategy {
    boolean isValidMove(Board board, Position from, Position to);
    List<Position> getPossibleMoves(Board board, Position current);
}