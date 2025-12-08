package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.GameState;
import com.BotTicTakToe.model.OccupantGameSymbol;
import com.BotTicTakToe.service.SessionManager;
import com.BotTicTakToe.service.ViewService;
import com.BotTicTakToe.view.ViewNameKey;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static com.BotTicTakToe.controller.CallBackData.MOVE;
import static com.BotTicTakToe.view.ViewNameKey.BOT_MESSAGE;

@Component
public class GameProcessHandler extends BaseCommandHandler implements CommandHandler {

    public GameProcessHandler(SessionManager sessionManager, TelegramClient telegramClient, ViewService viewService) {
        super(sessionManager, telegramClient, viewService);
    }

    @Override
    public boolean canHandle(Update update) {
        return update.hasCallbackQuery() && update.getCallbackQuery().getData().startsWith(MOVE.getData()) ;
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {
        long sessionId = update.getCallbackQuery().getMessage().getChatId();
        long playerId = update.getCallbackQuery().getFrom().getId();
        int messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackData = update.getCallbackQuery().getData();
        try {
            String[] data = callbackData.split("_");
            int row = Integer.parseInt(data[1]);
            int col = Integer.parseInt(data[2]);

            GameSession gameSession = sessionManager.makeMove(sessionId, playerId, row, col);

            setActionAfterMove(gameSession, sessionId,messageId, gameSession.getCurrentPlayer().getOccupant());

        } catch (IllegalStateException e) {
            sendNotification(update.getCallbackQuery().getId(), e.getMessage());
        }

    }

    private void setActionAfterMove(GameSession gameSession, long sessionId, int messageId, String occupant) {
        GameState currentState = gameSession.getGameState();

        EditMessageText text = viewService.editView(ViewNameKey.BOARD_MESSAGE.getName(), sessionId, messageId, gameSession);
        sendEditedResponse(text);

        if (currentState == GameState.X_WIN || currentState == GameState.O_WIN || currentState == GameState.DRAW) {
            SendMessage message = viewService.createView(BOT_MESSAGE.getName(), sessionId, gameSession);
            gameSession.setGameState(GameState.GAME_OVER);
            sendResponse(message);
        }
    }

    private void sendNotification(String callbackQueryId, String exceptionMessage) {

        AnswerCallbackQuery answerCallbackQuery = AnswerCallbackQuery.builder()
                .callbackQueryId(callbackQueryId)
                .text(exceptionMessage)
                .showAlert(false)
                .build();
        try {
            telegramClient.execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}
