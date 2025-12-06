package com.BotTicTakToe.model;

public class GameSession {
    private long sessionId;
    private Board board;
    private Player currentPlayer;
    private Player firstPlayer;
    private Player secondPlayer;
    private boolean isWaitingForPlayer = true;

    public GameSession(long sessionId, Player firstPlayer) {
        this.sessionId = sessionId;
        this.firstPlayer = firstPlayer;
        this.currentPlayer = firstPlayer;
        this.board = new Board();
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public boolean isWaitingForPlayer() {
        return isWaitingForPlayer;
    }

    public void setWaitingForPlayer(boolean waitingForPlayer) {
        isWaitingForPlayer = waitingForPlayer;
    }

    public void joinGame(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
        this.isWaitingForPlayer = false;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }
}
