package com.BotTicTakToe.controller;

public enum Command {
    START("/start"),
    JOIN("/join");

    private final String data;

    Command(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
