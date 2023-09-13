package skypro.TeamWorkTelegramBot.buttons;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * общий интерфейс для обработок кнопок
 */
public interface Command {
    void execute(Update update);
}
