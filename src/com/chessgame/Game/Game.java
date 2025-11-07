package com.chessgame.Game;

import java.awt.Graphics;
import javax.swing.JPanel;
import com.chessgame.Board.Board;
import com.chessgame.Pieces.Piece;

public class Game {
    private Board board;
    private GameController gameController;
    private GameStateManager stateManager;
    private MoveValidator moveValidator;
    private PromotionHandler promotionHandler;

    public static boolean currentPlayer = true; // true = white, false = black
    public Piece activePiece = null;
    public static boolean isDragging = false;

    public Game() {
        initializeGame();
    }

    private void initializeGame() {
        this.board = new Board();
        this.stateManager = new GameStateManager(this, board);
        this.moveValidator = new MoveValidator(board, stateManager);
        this.promotionHandler = new PromotionHandler(this, board, stateManager);
        this.gameController = new GameController(this, board, stateManager, moveValidator, promotionHandler);

        stateManager.loadStartingPosition();
        stateManager.initializeGameState();
    }

    public void draw(Graphics g, int x, int y, JPanel panel) {
        if (gameController != null) {
            gameController.draw(g, x, y, panel);
        }
    }

    public void selectPiece(int x, int y) {
        if (gameController != null) {
            gameController.selectPiece(x, y);
        }
    }

    public void move(int x, int y) {
        if (gameController != null) {
            gameController.move(x, y);
        }
    }

    // Getters
    public Board getBoard() { return board; }
    public GameController getGameController() { return gameController; }
    public GameStateManager getStateManager() { return stateManager; }
    public MoveValidator getMoveValidator() { return moveValidator; }
    public PromotionHandler getPromotionHandler() { return promotionHandler; }

    public static void switchPlayer() {
        currentPlayer = !currentPlayer;
    }

    public static boolean isWhiteTurn() {
        return currentPlayer;
    }
}