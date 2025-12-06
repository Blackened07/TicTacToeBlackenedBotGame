package com.BotTicTakToe;

import com.BotTicTakToe.controller.UpdateConsumer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;

@Component
public class TicTacToeBot implements SpringLongPollingBot {

    private final UpdateConsumer updateConsumer;

    public TicTacToeBot(UpdateConsumer updateConsumer) {
        this.updateConsumer = updateConsumer;
    }

    @Override
    public String getBotToken() {
        return "8284071932:AAFlYnH3T-wes5zPYUiazDfuoCEmEhj1UUU";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return updateConsumer;
    }
}
