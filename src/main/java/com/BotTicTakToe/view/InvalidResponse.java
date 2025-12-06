package com.BotTicTakToe.view;

import com.BotTicTakToe.model.GameSession;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class InvalidResponse implements CreateView<GameSession> {

    @Override
    public String getViewName() {
        return ViewNames.INVALID.getName();
    }

    @Override
    public SendMessage createView(long sessionId, GameSession data) {
        return SendMessage.builder()
                .text(data.getFirstPlayer() + "Это первый игрок! Или была другая команда?")
                .chatId(sessionId)
                .build();
    }
}
