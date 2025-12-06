package com.BotTicTakToe.model;

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
        this.currentPlayer = firstPlayer;
        this.board = new Board();
        this.gameState = GameState.WAITING_FOR_PLAYER;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public boolean isWaitingForPlayer() {
        return isWaitingForPlayer;
    }

    public void joinGame(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
        this.isWaitingForPlayer = false;
    }

    public String getCurrentPlayer() {
        return currentPlayer.getOccupant();
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
}
