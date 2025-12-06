package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.Command;
import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.service.SessionService;
import com.BotTicTakToe.service.ViewService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static com.BotTicTakToe.view.ViewNames.JOIN_BUTTON;

@Component
public class StartHandler implements CommandHandler {

    private final SessionService sessionService;
    private final TelegramClient telegramClient;
    private final ViewService viewService;

    public StartHandler(
            SessionService sessionService,
            TelegramClient telegramClient,
            ViewService viewService) {
        this.sessionService = sessionService;
        this.telegramClient = telegramClient;
        this.viewService = viewService;
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage() && update.getMessage().getText().equals(Command.START.getName());
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {

        long sessionId = update.getMessage().getChatId();
        long playerId = update.getMessage().getFrom().getId();

        sessionService.createGame(sessionId, playerId);
        SendMessage message = viewService.createView(JOIN_BUTTON.getName(), sessionId, sessionService.getSessions(sessionId));
        sendResponse(message);
    }

    @Override
    public void sendResponse(SendMessage message) {
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
