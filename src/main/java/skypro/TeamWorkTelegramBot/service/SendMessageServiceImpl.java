package skypro.TeamWorkTelegramBot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс, который создаёт сообщения и кнопки
 * для ответа пользователю
 */
@Service
public class SendMessageServiceImpl implements SendMessageService {

    /**
     * Перегруженный метод для отправки ответа пользователю, который принимает:
     * @param chatId          - id пользователя
     * @param textToSend      - текст сообщения для пользователя
     * @param buttonsText     - текст для кнопки
     * @param buttonsCallData - id кнопки
     * @throws RuntimeException
     */
    @Override
    public void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData, TelegramBotService telegramBotService) {
        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        SendMessage buttonsMessage = createButtons(sendMessage, buttonsText, buttonsCallData);
        try {
            telegramBotService.execute(buttonsMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Перегруженный метод для отправки ответа пользователю, который принимает:
     * @param chatId          - id пользователя
     * @param textToSend      - текст сообщения для пользователя
     * @throws RuntimeException
     */
    @Override
    public void SendMessageToUser(String chatId, String textToSend, TelegramBotService telegramBotService) {
        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        try {
            telegramBotService.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод, который создаёт кнопки. Принимает:
     * @param message         - сообщение для пользователя
     * @param buttonsText     - текст для кнопки
     * @param buttonsCallData - id кнопки
     */
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