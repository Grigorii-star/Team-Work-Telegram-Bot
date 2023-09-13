package skypro.TeamWorkTelegramBot.service;

/**
 * Интерфейс, который создаёт сообщения и кнопки
 * для ответа пользователю
 */
public interface SendMessageService {
    void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData, TelegramBotService telegramBotService);
    void SendMessageToUser(String chatId, String textToSend, TelegramBotService telegramBotService);
}
