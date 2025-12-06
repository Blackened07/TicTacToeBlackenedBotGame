package com.BotTicTakToe.service;

import com.BotTicTakToe.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManager {

    private final Map<Long, GameSession> sessions = new ConcurrentHashMap<>();

    public GameSession createGame(long sessionId, long playerId) {
        Player firstPlayer = new Player(playerId, OccupantGameSymbol.X.getSymbol());
        GameSession session = new GameSession(sessionId, firstPlayer);
        sessions.put(sessionId, session);
        return session;
    }

    public GameSession joinGame(long sessionId, long playerId) {
        GameSession session = sessions.get(sessionId);
        if (session != null && session.isWaitingForPlayer()) {
            if (playerId != session.getFirstPlayer().getId()) {
                Player secondPlayer = new Player(playerId, OccupantGameSymbol.O.getSymbol());
                session.setGameState(GameState.CONNECTION_SUCCESS);
                session.joinGame(secondPlayer);
            } else {
                session.setGameState(GameState.WAITING_ERROR);
            }
            return session;
        } else {
            return null;
        }
    }

    public GameSession getSessions(long sessionId) {
        return sessions.get(sessionId);
    }

}
