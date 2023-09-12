package skypro.TeamWorkTelegramBot.service;

import liquibase.pro.packaged.O;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import skypro.TeamWorkTelegramBot.buttons.Command;

import java.util.ArrayList;
import java.util.List;


@Component
public class SendMessageServiceImpl implements SendMessageService {
    private final TelegramBotService telegramBotService;

    @Lazy
    public SendMessageServiceImpl(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    @Override
    public void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData) {
        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        SendMessage buttonsMessage = createButtons(sendMessage, buttonsText, buttonsCallData);
        try {
            telegramBotService.execute(buttonsMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void SendMessageToUser(String chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        try {
            telegramBotService.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private SendMessage createButtons(SendMessage message, String[] buttonsText, String[] buttonsCallData) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        for (int j = 0; j < buttonsText.length; j++) {
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            InlineKeyboardButton buttonName = new InlineKeyboardButton();
            buttonName.setText(buttonsText[j]);
            buttonName.setCallbackData(buttonsCallData[j]);
            rowInline.add(buttonName);
            rowsInline.add(rowInline);
        }
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

}
