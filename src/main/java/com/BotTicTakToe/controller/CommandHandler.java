package com.BotTicTakToe.controller;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public interface CommandHandler {

    boolean canHandle(Update update);

    void handle(Update update, TelegramClient telegramClient);

}
