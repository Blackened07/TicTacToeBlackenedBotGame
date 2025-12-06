package com.BotTicTakToe.view;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface CreateView<T> {

    String getViewName();

    SendMessage createView(long sessionId, T data);

    default InlineKeyboardButton getButton(String buttonText, String callBackData) {
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callBackData)
                .build();
    };
}
