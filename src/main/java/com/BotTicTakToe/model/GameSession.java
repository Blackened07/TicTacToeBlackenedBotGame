package com.BotTicTakToe.model;

import com.BotTicTakToe.service.SessionManager;

public class GameSession {
    private final long sessionId;
    private Board board;
    private Player currentPlayer;
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean isWaitingForPlayer = true;
    private GameState gameState;

    public GameSession(long sessionId, Player firstPlayer) {
        this.sessionId = sessionId;
        this.firstPlayer = firstPlayer;
        this.board = new Board();
        this.gameState = GameState.WAITING_FOR_PLAYER;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public boolean isWaitingForPlayer() {
        return isWaitingForPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {

        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCurrentPlayerOccupant() {
        return currentPlayer.getOccupant();
    }

    public void switchCurrentPlayer() {
        if (this.currentPlayer == this.firstPlayer) {
            this.currentPlayer = this.secondPlayer;
        } else if (this.currentPlayer == this.secondPlayer) {
            this.currentPlayer = this.firstPlayer;
        }
    }

    public void joinSecondPlayerToGame(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
        this.isWaitingForPlayer = false;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    public int isWinOrDraw() {

        int drawValidator = 0;

        if (isWin()) {
            drawValidator = SessionManager.WIN_VALUE;
        } else if (checkFullBoard() == 9) {
            drawValidator = SessionManager.DRAW_VALUE;
        }
        return drawValidator;
    }

    private int checkFullBoard() {
        int count = 0;

        for (int i = 0; i < board.WIDTH; i++) {
            for (int j = 0; j < board.WIDTH; j++) {

                if (board.getCell(i, j).isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isWin() {
        int DELTA_FORWARD = 1;
        for (int i = 0; i < board.WIDTH; i++) {
            int DELTA_NONE = 0;
            if (checkLine(i, 0, DELTA_NONE, DELTA_FORWARD)) {
                return true;
            } else if (checkLine(0, i, DELTA_FORWARD, DELTA_NONE)) {
                return true;
            }
        }
        if (checkLine(0, 0, DELTA_FORWARD, DELTA_FORWARD)) {
            return true;
        } else {
            int DELTA_BACKWARD = -1;
            return checkLine(0, board.WIDTH - 1, DELTA_FORWARD, DELTA_BACKWARD);
        }
    }

    private boolean checkLine(int startRow, int startCol, int rowDirection, int colDirection) {
        Cell firstCell = board.getCell(startRow, startCol);

        if (!firstCell.isOccupied()) {
            return false;
        }

        String firstSymbol = firstCell.getOccupant().getSymbol();

        for (int i = 1; i < board.WIDTH; i++) {
            int currentRow = startRow + i * rowDirection;
            int currentCol = startCol + i * colDirection;

            Cell currentCell = board.getCell(currentRow, currentCol);

            if (!currentCell.isOccupied() || !currentCell.getOccupant().getSymbol().equals(firstSymbol)) {
                return false;
            }
        }
        return true;
    }


}
