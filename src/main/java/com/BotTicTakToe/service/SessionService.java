package com.BotTicTakToe.service;

import com.BotTicTakToe.model.Board;
import com.BotTicTakToe.model.GameSession;
import com.BotTicTakToe.model.OccupantGameSymbol;
import com.BotTicTakToe.model.Player;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private final Map<Long, GameSession> sessions = new ConcurrentHashMap<>();

    public GameSession createGame(long sessionId, long playerId) {
        Player firstPlayer = new Player(playerId, OccupantGameSymbol.X.getSymbol());
        GameSession session = new GameSession(sessionId, firstPlayer);
        sessions.put(sessionId, session);
        return session;
    }

    public GameSession joinGame(long sessionId,  long playerId) {
        Player secondPlayer = new Player(playerId, OccupantGameSymbol.O.getSymbol());
        GameSession session = sessions.get(sessionId);
        if (session != null && session.isWaitingForPlayer()) {
            session.joinGame(secondPlayer);
            return session;
        }
        return null;
    }

    public GameSession getSessions(long sessionId) {
        return sessions.get(sessionId);
    }

    public String getSymbol(long sessionId) {
        return null;
    }

    public Board getBoard(long id) {
        return sessions.get(id).getBoard();
    }

    public void setBoards(long sessionId) {
        Board board = new Board();
        sessions.get(sessionId).setBoard(board);
    }

}
