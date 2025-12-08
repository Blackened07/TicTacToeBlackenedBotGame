package com.BotTicTakToe.model;

import java.util.HashMap;
import java.util.Map;

public enum OccupantGameSymbol {
    X("X"),
    O("O");

    private final String symbol;

    private static final Map<String, OccupantGameSymbol> OCCUPANT_REGISTRY = new HashMap<>();

    static {
        for (OccupantGameSymbol symbol : OccupantGameSymbol.values()) {
            OCCUPANT_REGISTRY.put(symbol.symbol, symbol);
        }
    }

    OccupantGameSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static OccupantGameSymbol getSymbol(String symbol) {
        return OCCUPANT_REGISTRY.get(symbol);
    }
}
