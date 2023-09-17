package skypro.TeamWorkTelegramBot.service.sendMessageService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import java.util.ArrayList;
import java.util.List;


/**
 * Реализация интрефейса SendMessageService
 */
@Slf4j
@Service
public class SendMessageServiceImpl implements SendMessageService {

    /**
     * Перегруженный метод для отправки ответа пользователю, который принимает:
     * @param chatId          - id телеграмма пользователя
     * @param textToSend      - текст сообщения для пользователя
     * @param buttonsText     - текст для кнопки
     * @param buttonsCallData - id кнопки
     */
    @Override
    public void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData, TelegramBotService telegramBotService) {
        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        SendMessage buttonsMessage = createButtons(sendMessage, buttonsText, buttonsCallData);
        try {
            telegramBotService.execute(buttonsMessage);
        } catch (TelegramApiException e) {
            log.error("TelegramApiException in SendMessageToUser method");
        }
    }

    /**
     * Перегруженный метод для отправки ответа пользователю, который принимает:
     * @param chatId          - id пользователя
     * @param textToSend      - текст сообщения для пользователя
     */
    @Override
    public void SendMessageToUser(String chatId, String textToSend, TelegramBotService telegramBotService) {
        SendMessage sendMessage = new SendMessage(chatId, textToSend);

        try {
            telegramBotService.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("TelegramApiException in SendMessageToUser method");
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