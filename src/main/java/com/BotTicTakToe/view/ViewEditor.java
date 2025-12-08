package com.BotTicTakToe.view;

import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public interface ViewEditor<T> {

    EditMessageText buildEditMessage(Long sessionId, int messageId, T data);

    String getViewName();

    default InlineKeyboardButton getButton(String buttonText, String callBackData) {
        return InlineKeyboardButton.builder()
                .text(buttonText)
                .callbackData(callBackData)
                .build();
    };
}

