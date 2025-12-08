package com.BotTicTakToe.service;

import com.BotTicTakToe.view.CreateView;
import com.BotTicTakToe.view.ViewEditor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ViewService {

    private final Map<String, CreateView<?>> views;
    private final Map<String, ViewEditor<?>> editors;

    public ViewService(List<CreateView<?>> views, List<ViewEditor<?>> editors) {
        this.views = views.stream()
                .collect(Collectors.toMap(CreateView::getViewName, creator -> creator));
        this.editors = editors.stream()
                .collect(Collectors.toMap(ViewEditor::getViewName, creator -> creator));
    }

    public <T> SendMessage createView(String viewName, long sessionId, T data) {
        CreateView<T> view = (CreateView<T>) views.get(viewName);
        if (view == null) {
            throw new IllegalArgumentException(viewName + " not found");
        }
        return view.createView(sessionId, data);
    }

    public <T> EditMessageText editView(String viewName, long sessionId, int messageId, T data) {
        ViewEditor<T> editor = (ViewEditor<T>) editors.get(viewName);
        if (editor == null) {
            throw new IllegalArgumentException(viewName + " not found");
        }
        return editor.buildEditMessage(sessionId, messageId, data);
    }

    public SendMessage setResponseMessage(Long chatId, String botText) {
        return SendMessage.builder()
                .text(botText)
                .chatId(chatId)
                .build();

    }

}
