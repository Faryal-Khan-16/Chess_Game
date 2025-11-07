package com.chessgame.Board;

import java.util.*;
import com.chessgame.Pieces.Piece;

public class Board implements Cloneable {
    public static final int ROWS = 8;
    public static final int COLUMNS = 8;

    private int[][] grid;
    private Piece[][] pieces;
    private Stack<Move> moveHistory;
    private Stack<Piece> capturedPieces;
    private List<Piece> piecesList;

    public Board() {
        grid = new int[ROWS][COLUMNS];
        pieces = new Piece[ROWS][COLUMNS];
        moveHistory = new Stack<>();
        capturedPieces = new Stack<>();
        piecesList = new ArrayList<>();
        initializeEmptyBoard();
    }

    private void initializeEmptyBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                grid[i][j] = 0;
                pieces[i][j] = null;
            }
        }
    }

    public void setPieceIntoBoard(int x, int y, Piece piece) {
        if (!isValidPosition(x, y)) return;

        if (piece != null) {
            grid[x][y] = piece.getValueInTheboard();
            pieces[x][y] = piece;
            if (!piecesList.contains(piece)) {
                piecesList.add(piece);
            }
        } else {
            grid[x][y] = 0;
            pieces[x][y] = null;
        }
    }

    public void updatePieces(int fromX, int fromY, int toX, int toY, Piece piece) {
        if (!isValidPosition(fromX, fromY) || !isValidPosition(toX, toY) || piece == null) return;

        Move move = new Move(fromX, fromY, toX, toY, piece);
        moveHistory.push(move);

        // Check if there's a piece at destination (capture)
        Piece capturedPiece = pieces[toX][toY];
        if (capturedPiece != null) {
            capturedPieces.push(capturedPiece);
            piecesList.remove(capturedPiece);
            capturedPiece.setAlive(false);
        } else {
            capturedPieces.push(null);
        }

        // CRITICAL FIX: Clear the original position FIRST
        grid[fromX][fromY] = 0;
        pieces[fromX][fromY] = null;

        // Then set the new position
        grid[toX][toY] = piece.getValueInTheboard();
        pieces[toX][toY] = piece;

        // Update piece position
        piece.setXcord(toX);
        piece.setYcord(toY);
    }

    public boolean makeMove(int fromX, int fromY, int toX, int toY) {
        Piece piece = getPiece(fromX, fromY);
        if (piece == null) {
            System.out.println("No piece at source position (" + fromX + "," + fromY + ")");
            return false;
        }

        System.out.println("Moving " + piece.getClass().getSimpleName() + " from (" + fromX + "," + fromY + ") to (" + toX + "," + toY + ")");

        updatePieces(fromX, fromY, toX, toY, piece);
        return true;
    }

    public void undoMove() {
        if (moveHistory.isEmpty()) return;

        Move move = moveHistory.pop();
        Piece captured = capturedPieces.pop();

        // Restore moving piece to original position
        Piece movingPiece = move.getPiece();
        grid[move.getFromX()][move.getFromY()] = movingPiece.getValueInTheboard();
        pieces[move.getFromX()][move.getFromY()] = movingPiece;
        movingPiece.setXcord(move.getFromX());
        movingPiece.setYcord(move.getFromY());

        // Restore captured piece if any
        if (captured != null) {
            piecesList.add(captured);
            captured.setAlive(true);
            grid[move.getToX()][move.getToY()] = captured.getValueInTheboard();
            pieces[move.getToX()][move.getToY()] = captured;
            captured.setXcord(move.getToX());
            captured.setYcord(move.getToY());
        } else {
            grid[move.getToX()][move.getToY()] = 0;
            pieces[move.getToX()][move.getToY()] = null;
        }
    }

    public Piece getPiece(int x, int y) {
        if (isValidPosition(x, y)) {
            return pieces[x][y];
        }
        return null;
    }

    public int getXY(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        }
        return -1;
    }

    public void setXY(int x, int y, int value) {
        if (isValidPosition(x, y)) {
            grid[x][y] = value;
        }
    }

    public Board getNewBoard() {
        Board newBoard = new Board();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Piece originalPiece = this.getPiece(i, j);
                if (originalPiece != null) {
                    newBoard.setPieceIntoBoard(i, j, originalPiece.getClone());
                }
            }
        }
        return newBoard;
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < ROWS && y >= 0 && y < COLUMNS;
    }

    // Getters
    public int[][] getGrid() {
        int[][] copy = new int[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            copy[i] = grid[i].clone();
        }
        return copy;
    }

    public List<Piece> getPiecesList() {
        return new ArrayList<>(piecesList);
    }

    public Stack<Move> getMoveHistory() {
        return (Stack<Move>) moveHistory.clone();
    }

    public boolean isMoveHistoryEmpty() {
        return moveHistory.isEmpty();
    }

    public void printBoard() {
        System.out.println("Current Board State:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                System.out.print(grid[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printPiecesBoard() {
        System.out.println("Pieces Board State:");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (pieces[i][j] != null) {
                    System.out.print(pieces[i][j].getClass().getSimpleName().charAt(0) + "\t");
                } else {
                    System.out.print(".\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}