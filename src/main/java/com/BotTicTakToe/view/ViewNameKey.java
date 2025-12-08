package com.BotTicTakToe.view;

public enum ViewNameKey {
    JOIN_BUTTON("JoinButton"),
    BOT_MESSAGE("BotMessage"),
    BOARD_MESSAGE("BoardMessage"),
    X_WIN("XWin"),
    O_WIN("OWin"),
    DRAW("Draw"),;

    private final String name;

    ViewNameKey(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
