package skypro.TeamWorkTelegramBot.service;

/**
 * Интерфейс, который создаёт сообщения и кнопки
 * для ответа пользователю
 */
public interface SendMessageService {
    void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData);
    void SendMessageToUser(String chatId, String textToSend);
}
