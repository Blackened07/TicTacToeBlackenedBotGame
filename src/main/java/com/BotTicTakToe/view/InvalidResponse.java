package com.BotTicTakToe.view;

import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.GameState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class InvalidResponse implements CreateView<GameSession> {

    @Override
    public String getViewName() {
        return ViewNameKey.INVALID.getName();
    }

    @Override
    public SendMessage createView(long sessionId, GameSession data) {
        String message = "";
        if (data.getGameState() == GameState.WAITING_ERROR) {
            message = "";
        } else if (data.getGameState() == GameState.GAME_SESSION_NOT_EXISTS) {
            message = "";
        }

        return SendMessage.builder()
                .text(data.getGameState().toString() + message)
                .chatId(sessionId)
                .build();
    }

}
