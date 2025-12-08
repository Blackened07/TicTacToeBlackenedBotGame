package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.CallBackData;
import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.GameState;
import com.BotTicTakToe.service.SessionManager;
import com.BotTicTakToe.service.ViewService;
import com.BotTicTakToe.view.ViewNameKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class JoinHandler extends BaseCommandHandler implements CommandHandler {

    public JoinHandler(SessionManager sessionManager, TelegramClient telegramClient, ViewService viewService) {
        super(sessionManager, telegramClient, viewService);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(CallBackData.JOIN.getData());
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {
        var updateCallbackQuery = update.getCallbackQuery();
        long sessionId = updateCallbackQuery.getMessage().getChatId();
        long playerId = updateCallbackQuery.getFrom().getId();
        String name = updateCallbackQuery.getFrom().getFirstName();

        int messageId = updateCallbackQuery.getMessage().getMessageId();

        GameSession gameSession = sessionManager.joinGame(sessionId, playerId, name);

        if (gameSession.getGameState() == GameState.CONNECTION_SUCCESS) {
            gameSession.setGameState(GameState.GAME_ON);
            EditMessageText messageText = viewService.editView(ViewNameKey.BOARD_MESSAGE.getName(), sessionId, messageId, gameSession);
            sendEditedResponse(messageText);
        } else {
            SendMessage message = viewService.createView(ViewNameKey.BOT_MESSAGE.getName(), sessionId, gameSession);
            sendResponse(message);
        }
    }
}
