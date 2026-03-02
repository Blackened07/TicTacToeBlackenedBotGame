package com.BotTicTakToe.view;

import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.GameState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class BotResponse implements CreateView<GameSession> {

    private final static String END_MESSAGE_FOR_ALL = "\nВведите /start для начала новой игры!";

    @Override
    public String getViewName() {
        return ViewNameKey.BOT_MESSAGE.getName();
    }

    @Override
    public SendMessage createView(long sessionId, GameSession data) {
        GameState currentState = data.getGameState();

        String message = "";
        if (currentState == GameState.CONNECTION_FAILURE) {
            message = "Это многопользовательская игра! Вы не можете играть с самим собой, а я не запрограммирован играть с вами, извините и да хранит вас Господь! ";
        } else if (currentState == GameState.GAME_SESSION_NOT_EXISTS) {
            message = "Давай по новой Миша, всё хуйня";
        } else if (currentState == GameState.CONNECTION_SUCCESS) {
            message = "Игрок " + data.getSecondPlayer().getId() + " присоединился к игре!";
        } else if (currentState == GameState.X_WIN || currentState == GameState.O_WIN) {
            message = String.format("ИГРОК %s ПОБЕДИЛ!!!%s", data.getCurrentPlayer().getName(), END_MESSAGE_FOR_ALL );
        } else if (currentState == GameState.DRAW) {
            message = String.format("НИЧЬЯЧЬЯ%s", END_MESSAGE_FOR_ALL );
        }

        return SendMessage.builder()
                .text(message)
                .chatId(sessionId)
                .build();
    }

}
