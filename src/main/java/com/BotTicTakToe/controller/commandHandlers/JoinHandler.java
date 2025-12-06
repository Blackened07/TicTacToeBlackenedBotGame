package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.Command;
import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.service.SessionService;
import com.BotTicTakToe.service.ViewService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class JoinHandler extends BaseCommandHandler implements CommandHandler {

    public JoinHandler(SessionService sessionService, TelegramClient telegramClient, ViewService viewService) {
        super(sessionService, telegramClient, viewService);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasCallbackQuery() && update.getMessage().getText().equals(Command.JOIN.getData());
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {

    }

    @Override
    protected void sendResponse(SendMessage message) {
        super.sendResponse(message);
    }

}
