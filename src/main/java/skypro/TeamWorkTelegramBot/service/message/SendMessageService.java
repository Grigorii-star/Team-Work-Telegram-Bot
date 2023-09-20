package skypro.TeamWorkTelegramBot.service.message;

import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

/**
 * Интерфейс, который создаёт сообщения и кнопки
 * для ответа пользователю.
 */
public interface SendMessageService {
    /**
     * Метод для создания кнопок.
     * @param chatId - id Telegram пользователя.
     * @param textToSend - текст сообщения для пользователя.
     * @param buttonsText - текст для кнопки.
     * @param buttonsCallData id кнопки.
     * @param telegramBotService
     */
    void SendMessageToUserWithButtons(String chatId, String textToSend, String[] buttonsText,
                                      String[] buttonsCallData, TelegramBotService telegramBotService);

    /**
     * метод для передачи сообщения пользователю.
     * @param chatId- id Telegram пользователя.
     * @param textToSend - текст сообщения для пользователя.
     * @param telegramBotService
     */
    void SendMessageToUser(String chatId, String textToSend, TelegramBotService telegramBotService);
}
