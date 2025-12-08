package com.BotTicTakToe.controller;

public enum CallBackData {
    START("/start"),
    JOIN("/join"),
    MOVE("MOVE_");

    private final String data;

    CallBackData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
