package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.service.SessionManager;
import com.BotTicTakToe.service.ViewService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public abstract class BaseCommandHandler implements CommandHandler {

    final SessionManager sessionManager;
    final TelegramClient telegramClient;
    final ViewService viewService;

    public BaseCommandHandler(
            SessionManager sessionManager,
            TelegramClient telegramClient,
            ViewService viewService) {
        this.sessionManager = sessionManager;
        this.telegramClient = telegramClient;
        this.viewService = viewService;
    }

    protected void sendResponse(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    protected void sendEditedResponse(EditMessageText message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
