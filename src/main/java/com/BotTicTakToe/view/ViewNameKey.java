package com.BotTicTakToe.view;

public enum ViewNameKey {
    JOIN_BUTTON("JoinButton"),
    INVALID("Invalid"),;

    private final String name;

    ViewNameKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
