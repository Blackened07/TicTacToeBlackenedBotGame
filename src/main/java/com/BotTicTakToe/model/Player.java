package com.BotTicTakToe.model;

public class Player {
    private final String name;
    private final long id;
    private final String occupant;

    public Player(long id, String occupant, String name) {
        this.id = id;
        this.occupant = occupant;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String getOccupant() {
        return occupant;
    }

}
