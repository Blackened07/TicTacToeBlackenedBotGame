package com.BotTicTakToe.service;

import com.BotTicTakToe.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManager {

    public final static int WIN_VALUE = 1;
    public final static int DRAW_VALUE = -1;

    private final Map<Long, GameSession> sessions = new ConcurrentHashMap<>();

    public GameSession createGame(long sessionId, long playerId, String name) {
        Player firstPlayer = new Player(playerId, setRandomSymbol(), name);
        GameSession session = new GameSession(sessionId, firstPlayer);
        sessions.put(sessionId, session);
        return session;
    }

    public GameSession joinGame(long sessionId, long playerId, String name) {
        GameSession session = sessions.get(sessionId);
        if (session != null && session.isWaitingForPlayer()) {
            if (playerId != session.getFirstPlayer().getId()) {
                Player secondPlayer = new Player(playerId, checkFirstPlayerSymbol(session), name);
                session.setGameState(GameState.CONNECTION_SUCCESS);
                session.joinSecondPlayerToGame(secondPlayer);

                choseRandomPlayer(session);
            } else {
                session.setGameState(GameState.CONNECTION_FAILURE);
            }
            return session;
        } else {
            return null;
        }
    }

    public GameSession makeMove(long sessionId, long playerId, int row, int col) {
        GameSession gameSession = sessions.get(sessionId);
        Player currentPlayer = gameSession.getCurrentPlayer();
        Cell destCell = gameSession.getBoard().getCell(row, col);

        if (playerId == currentPlayer.getId()) {
            if (!destCell.isOccupied()) {
                String curPLayerSymbol = currentPlayer.getOccupant();
                destCell.setOccupant(new Occupant(curPLayerSymbol));

                int result = gameSession.isWinOrDraw();

                if (result ==  WIN_VALUE) {
                    GameState gameState = curPLayerSymbol.equals(OccupantGameSymbol.X.getSymbol()) ? GameState.X_WIN : GameState.O_WIN;
                    gameSession.setGameState(gameState);
                    return gameSession;
                } else if (result == DRAW_VALUE) {
                    gameSession.setGameState(GameState.DRAW);
                    return gameSession;
                } else {
                    gameSession.switchCurrentPlayer();
                }
            } else {
                throw new IllegalStateException("Эта клетка уже занята");
            }
        } else {
            throw new IllegalStateException("Сейчас не ваш ход!");
        }
        return gameSession;
    }

    public GameSession getSessions(long sessionId) {
        return sessions.get(sessionId);
    }

    private String setRandomSymbol() {
        if (Math.random() < 0.5) {
            return OccupantGameSymbol.X.getSymbol();
        } else {
            return OccupantGameSymbol.O.getSymbol();
        }
    }

    private String checkFirstPlayerSymbol(GameSession session) {
        if (session.getFirstPlayer().getOccupant().equals(OccupantGameSymbol.X.getSymbol())) {
            return OccupantGameSymbol.O.getSymbol();
        } else {
            return OccupantGameSymbol.X.getSymbol();
        }
    }

    private void choseRandomPlayer(GameSession session) {
        if (Math.random() < 0.5) {
            session.setCurrentPlayer(session.getFirstPlayer());
        } else {
            session.setCurrentPlayer(session.getSecondPlayer());
        }
    }
}
