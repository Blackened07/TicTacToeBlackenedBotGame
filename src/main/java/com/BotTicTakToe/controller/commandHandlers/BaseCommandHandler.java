package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.service.SessionService;
import com.BotTicTakToe.service.ViewService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public abstract class BaseCommandHandler implements CommandHandler {

    final SessionService sessionService;
    final TelegramClient telegramClient;
    final ViewService viewService;

    public BaseCommandHandler(
            SessionService sessionService,
            TelegramClient telegramClient,
            ViewService viewService) {
        this.sessionService = sessionService;
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
}
