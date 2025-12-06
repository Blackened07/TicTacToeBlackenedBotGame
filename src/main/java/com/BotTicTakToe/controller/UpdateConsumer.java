package com.BotTicTakToe.controller;

import com.BotTicTakToe.service.SessionService;
import com.BotTicTakToe.service.ViewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static com.BotTicTakToe.view.ViewNames.JOIN_BUTTON;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {

    private final TelegramClient telegramClient;
    private final SessionService sessionService;
    private final ViewService viewService;
    private final String BOT_TOKEN;

    public UpdateConsumer(
            TelegramClient telegramClient,
            SessionService sessionService,
            ViewService viewService,
            @Value("${tg.token}") String botToken) {
        this.telegramClient = telegramClient;
        this.sessionService = sessionService;
        this.viewService = viewService;
        this.BOT_TOKEN = botToken;
    }

    @Override
    public void consume(Update update) {

        if (update.hasMessage()) {
            long sessionId = update.getMessage().getChatId();
            long playerId = update.getMessage().getFrom().getId();

            if(update.getMessage().getText().equals("/start")) {
                sessionService.createGame(sessionId, playerId);
                SendMessage message = viewService.createView(JOIN_BUTTON.getName(), sessionId, sessionService.getSessions(sessionId));
                sendResponse(message);
            } else {
                SendMessage message = viewService.setResponseMessage(update.getMessage().getChatId(), "Что?");
                sendResponse(message);
            }
        } else if (update.hasCallbackQuery()) {
            long sessionId = update.getCallbackQuery().getMessage().getChatId();
            long playerId = update.getCallbackQuery().getFrom().getId();
            String data = update.getCallbackQuery().getData();

        }
    }

    private void sendResponse(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}
