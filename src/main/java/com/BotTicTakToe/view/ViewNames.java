package com.BotTicTakToe.view;

public enum ViewNames {
    JOIN_BUTTON("JoinButton");

    private final String name;

    ViewNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
