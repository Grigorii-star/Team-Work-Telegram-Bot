package skypro.TeamWorkTelegramBot.buttons;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

/**
 * интерфейс для обработок значений от телеграмм бота
 */
public interface Command {
    /**
     * Универсальный метод для получения значений из телеграмм бота
     * @param update объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    default void updatesExtractor(Update update, TelegramBotService telegramBotService) {}
    default void messagesExtractor(Message message, TelegramBotService telegramBotService){}
    default void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService){}
}
