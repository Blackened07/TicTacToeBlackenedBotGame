package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.Command;
import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.GameState;
import com.BotTicTakToe.service.SessionManager;
import com.BotTicTakToe.service.ViewService;
import com.BotTicTakToe.view.ViewNameKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class JoinHandler extends BaseCommandHandler implements CommandHandler {

    public JoinHandler(SessionManager sessionManager, TelegramClient telegramClient, ViewService viewService) {
        super(sessionManager, telegramClient, viewService);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(Command.JOIN.getData());
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {
        long sessionId = update.getCallbackQuery().getMessage().getChatId();
        long playerId = update.getCallbackQuery().getFrom().getId();

        GameSession gameSession = sessionManager.joinGame(sessionId, playerId);

        SendMessage message = viewService.createView(ViewNameKey.INVALID.getName(), sessionId, gameSession);
        sendResponse(message);

    }

    @Override
    protected void sendResponse(SendMessage message) {
        super.sendResponse(message);
    }

    private boolean isSessionExist(GameSession gameSession) {
        return gameSession != null;
    }
}
