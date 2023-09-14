package skypro.TeamWorkTelegramBot.service;

/**
 * Интерфейс, который создаёт сообщения и кнопки
 * для ответа пользователю
 */
public interface SendMessageService {
    /**
     * Метод для создания кнопок
     * @param chatId - id телеграма пользователя
     * @param textToSend - текст сообщения для пользователя
     * @param buttonsText - текст для кнопки
     * @param buttonsCallData id кнопки
     * @param telegramBotService
     */
    void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData, TelegramBotService telegramBotService);

    /**
     * метод для передачи сообщения пользователю
     * @param chatId- id телеграма пользователя
     * @param textToSend - текст сообщения для пользователя
     * @param telegramBotService
     */
    void SendMessageToUser(String chatId, String textToSend, TelegramBotService telegramBotService);
}
