package com.BotTicTakToe.view;

import com.BotTicTakToe.model.GameSession;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.List;
@Component
public class JoinButton implements CreateView<GameSession> {

    @Override
    public String getViewName() {
        return ViewNames.JOIN_BUTTON.getName();
    }

    @Override
    public SendMessage createView(long sessionId, GameSession data) {

        List<InlineKeyboardButton> joinButton = List.of(
                getButton("Присоединиться к игре", "Join")
        );

        List<InlineKeyboardRow> keyboardRows = List.of(
                new InlineKeyboardRow(joinButton.getFirst())
        );

        String textButt = "Пользователь: " + data.getFirstPlayer().getId() + " создал игру!";

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup(keyboardRows);

        return SendMessage.builder()
                .text(textButt)
                .chatId(sessionId)
                .replyMarkup(markup)
                .build();
    }


}
