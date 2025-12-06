package com.BotTicTakToe.model;

public enum OccupantGameSymbol {
    X("X"),
    O("O");

    private final String symbol;

    OccupantGameSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
