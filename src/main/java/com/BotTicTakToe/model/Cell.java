package com.BotTicTakToe.model;

public class Cell {

    private int x;
    private int y;
    private Occupant occupant;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.occupant = new Occupant("");
    }

    public Occupant getOccupant() {
        return occupant;
    }

    public void setOccupant(Occupant occupant) {
        this.occupant = occupant;
    }

    public boolean isOccupied() {
        return !occupant.getSymbol().isEmpty();
    }
}
