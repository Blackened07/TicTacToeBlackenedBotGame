package com.BotTicTakToe.view;

import com.BotTicTakToe.model.Board;
import com.BotTicTakToe.model.Cell;
import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.Occupant;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;


@Component
public class BoardView implements ViewEditor<GameSession>, CreateView<GameSession> {

    private static final String PLAYER_TURN_MESSAGE = "ХОД: ";
    private static final String X_WINNER_MESSAGE = "Выйграл игрок Х";
    private static final String O_WINNER_MESSAGE = "Выйграл игрок О";
    private static final String DRAW_MESSAGE = "Ничья";
    private static final String DEFAULT_MESSAGE = "Что-то пошло не так";
    private static final String EMPTY_CELL = " ";
    private static final String NO_OP = " ";

    @Override
    public String getViewName() {
        return ViewNameKey.BOARD_MESSAGE.getName();
    }

    /// FOR FIRST SHOW
    @Override
    public SendMessage createView(long sessionId, GameSession data) {
        return SendMessage.builder()
                .chatId(sessionId)
                .text(buildMessageText(data))
                .replyMarkup(buildGameBoardMarkup(data))
                .build();

    }

    ///  FOR NEXT
    @Override
    public EditMessageText buildEditMessage(Long sessionId, int messageId, GameSession data) {
        return EditMessageText.builder()
                .chatId(sessionId)
                .messageId(messageId)
                .text(buildMessageText(data))
                .replyMarkup(buildGameBoardMarkup(data))
                .build();
    }

    private InlineKeyboardMarkup buildGameBoardMarkup(GameSession data) {
        Board board = data.getBoard();

        List<InlineKeyboardRow> keyboardRow = new ArrayList<>();

        for (int i = 0; i < board.HEIGHT; i++) {
            List<InlineKeyboardButton> buttons = new ArrayList<>();
            for (int j = 0; j < board.WIDTH; j++) {

                Cell currentCell = board.getCell(i, j);
                InlineKeyboardButton currentButton;
                if (currentCell.isOccupied()) {
                    Occupant occupant = currentCell.getOccupant();
                    currentButton = getButton(occupant.getSymbol(), NO_OP);
                } else {
                   currentButton = getButton(EMPTY_CELL, "MOVE_" + i + "_" + j);
                }
                buttons.add(currentButton);
            }
            InlineKeyboardRow row = new InlineKeyboardRow(buttons);
            keyboardRow.add(row);
        }
        return new InlineKeyboardMarkup(keyboardRow);
    }


    @Override
    public InlineKeyboardButton getButton(String buttonText, String callBackData) {
        return ViewEditor.super.getButton(buttonText, callBackData);
    }

    private String buildMessageText(GameSession data) {
        switch (data.getGameState()) {
            case DRAW -> {
                return DRAW_MESSAGE;
            }
            case O_WIN -> {
                return O_WINNER_MESSAGE;
            }
            case X_WIN -> {
                return X_WINNER_MESSAGE;
            }
            case GAME_ON -> {
                return String.format("%s %s %s", PLAYER_TURN_MESSAGE, data.getCurrentPlayerOccupant(), data.getCurrentPlayer().getName());

            }
        }
        return DEFAULT_MESSAGE;
    }

}
