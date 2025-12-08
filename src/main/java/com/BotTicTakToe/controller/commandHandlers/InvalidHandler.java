package com.BotTicTakToe.controller.commandHandlers;

import com.BotTicTakToe.controller.CommandHandler;
import com.BotTicTakToe.service.SessionManager;
import com.BotTicTakToe.service.ViewService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static com.BotTicTakToe.view.ViewNameKey.BOT_MESSAGE;

@Component
public class InvalidHandler extends BaseCommandHandler implements CommandHandler {

    public InvalidHandler(SessionManager sessionManager, TelegramClient telegramClient, ViewService viewService) {
        super(sessionManager, telegramClient, viewService);
    }

    @Override
    public boolean canHandle(Update update) {
        return false/*update.hasMessage() && !update.getMessage().getText().equals(Command.START.getData())*/ ;
    }

    @Override
    public void handle(Update update, TelegramClient telegramClient) {
        long sessionId = update.getMessage().getChatId();

        sendResponse(viewService.createView(BOT_MESSAGE.getName(), sessionId, sessionManager.getSessions(sessionId)));
    }

    @Override
    protected void sendResponse(SendMessage message) {
        super.sendResponse(message);
    }
}
