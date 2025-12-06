package com.BotTicTakToe.view;

public enum ViewNamePlates {
    START("Добро пожаловать в игру крестики-нолики! И да поможет вам..."),
    JOIN("Вы присоединились к игре!");

    private final String name;

    ViewNamePlates(String name) {
        this.name = name;
    }

    public String getData() {
        return name;
    }
}
