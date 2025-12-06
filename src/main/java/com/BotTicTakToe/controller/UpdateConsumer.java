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

import java.util.List;

import static com.BotTicTakToe.view.ViewNames.INVALID;
import static com.BotTicTakToe.view.ViewNames.JOIN_BUTTON;

@Component
public class UpdateConsumer implements LongPollingSingleThreadUpdateConsumer, SpringLongPollingBot {

    private final TelegramClient telegramClient;
    private final SessionService sessionService;
    private final ViewService viewService;
    private final String BOT_TOKEN;

    private final List<CommandHandler> commandHandlers;

    public UpdateConsumer(
            TelegramClient telegramClient,
            SessionService sessionService,
            ViewService viewService,
            @Value("${tg.token}") String botToken,
            List<CommandHandler> commandHandlers)
    {
        this.telegramClient = telegramClient;
        this.sessionService = sessionService;
        this.viewService = viewService;
        this.BOT_TOKEN = botToken;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public void consume(Update update) {
        //Переносим логику в классы, доступ к классам через commandHandlers
        for (CommandHandler handler : commandHandlers) {
            if (handler.canHandle(update)) {
                handler.handle(update, telegramClient);
                return;
            }
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
