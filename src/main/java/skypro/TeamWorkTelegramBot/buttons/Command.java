package skypro.TeamWorkTelegramBot.buttons;

import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

/**
 * интерфейс для обработок значений от телеграмм бота
 */
public interface Command {
    /**
     * Универсальный метод для получения значений из телеграмм бота
     * @param update объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    void execute(Update update, TelegramBotService telegramBotService);
}
