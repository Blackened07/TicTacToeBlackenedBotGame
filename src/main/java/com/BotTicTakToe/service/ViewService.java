package com.BotTicTakToe.service;

import com.BotTicTakToe.view.CreateView;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ViewService {

    private final Map<String, CreateView<?>> views;

    public ViewService(List<CreateView<?>> views) {
        this.views = views.stream()
                .collect(Collectors.toMap(CreateView::getViewName, creator -> creator));
    }

    public <T> SendMessage createView(String viewName, long sessionId, T data) {
        CreateView<T> view = (CreateView<T>) views.get(viewName);
        if (view == null) {
            throw new IllegalArgumentException(viewName + " not found");
        }
        return view.createView(sessionId, data);
    }

    public SendMessage setResponseMessage(Long chatId, String botText) {
        return SendMessage.builder()
                .text(botText)
                .chatId(chatId)
                .build();

    }
}
