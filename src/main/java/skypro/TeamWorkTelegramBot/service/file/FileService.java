package skypro.TeamWorkTelegramBot.service.file;

import org.telegram.telegrambots.meta.api.objects.Message;
import skypro.TeamWorkTelegramBot.entity.Report;

/**
 * Интерфейс для для сохранения отчетов о животном Report в БД.
 */
public interface FileService {
    /**
     * Метод обрабатывает Message сообщения
     * достает из них все необходимые данные.
     *
     * @param telegramMessage - объект Telegram для получения значений из Telegram бота.
     * @return объект Report сохраненный в БД.
     */
    Report animalReport (Message telegramMessage);
}
