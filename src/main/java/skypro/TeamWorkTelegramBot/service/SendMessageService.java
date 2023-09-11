package skypro.TeamWorkTelegramBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SendMessageService {
    private final TelegramBotService telegramBotService;

    public SendMessageService(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    public void SendMessageToUser(String chatId, String textToSend, InlineKeyboardMarkup buttons) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);
        sendMessage.setReplyMarkup(buttons);
        try {
            telegramBotService.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


//    public InlineKeyboardMarkup createMenuDoubleButtons(String ... buttons) {
//        int length = buttons.length;
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        for (int i = 0; i < length / 2; i++) {
//            InlineKeyboardButton button1 = new InlineKeyboardButton(buttons[i*2]);
//            InlineKeyboardButton button2 = new InlineKeyboardButton(buttons[i*2+1]);
//            button1.setCallbackData(button1.getText());
//            button2.setCallbackData(button2.getText());
//            keyboardMarkup.(button1, button2);
//        }
//        if (length % 2 != 0) {
//            InlineKeyboardButton button1 = new InlineKeyboardButton(buttons[length - 1]);
//            button1.setCallbackData(button1.getText());
//            keyboardMarkup.setKeyboard(button1);
//        }
//        return keyboardMarkup;
//    }
}
