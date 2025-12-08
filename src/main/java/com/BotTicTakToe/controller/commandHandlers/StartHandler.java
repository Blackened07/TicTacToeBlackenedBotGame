package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.CallBackData;
import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.service.SessionManager;
import com.BotTicTakToe.service.ViewService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static com.BotTicTakToe.view.ViewNameKey.JOIN_BUTTON;

@Component
public class StartHandler extends BaseCommandHandler implements CommandHandler {

    public StartHandler(SessionManager sessionManager, TelegramClient telegramClient, ViewService viewService) {
        super(sessionManager, telegramClient, viewService);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage() && CallBackData.START.getData().equals(update.getMessage().getText());
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {
        var updateMessage = update.getMessage();
        long sessionId = updateMessage.getChatId();
        long playerId = updateMessage.getFrom().getId();
        String name = updateMessage.getFrom().getFirstName();

        GameSession gameSession = sessionManager.createGame(sessionId, playerId, name);
        SendMessage message = viewService.createView(JOIN_BUTTON.getName(), sessionId, gameSession);
        sendResponse(message);
    }

    @Override
    protected void sendResponse(SendMessage message) {
        super.sendResponse(message);
    }

}
