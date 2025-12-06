package com.BotTicTakToe.model;

public class Player {

    private final long id;
    private final String occupant;
    private boolean turn;

    public Player(long id, String occupant) {
        this.id = id;
        this.occupant = occupant;
    }

    public long getId() {
        return id;
    }

    public String getOccupant() {
        return occupant;
    }

    public boolean getTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
