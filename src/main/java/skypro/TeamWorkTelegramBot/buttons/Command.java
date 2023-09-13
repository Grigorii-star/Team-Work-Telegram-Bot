package skypro.TeamWorkTelegramBot.buttons;

import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

/**
 * общий интерфейс для обработок кнопок
 */
public interface Command {
    void execute(Update update, TelegramBotService telegramBotService);
}
