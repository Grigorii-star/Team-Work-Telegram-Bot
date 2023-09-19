package skypro.TeamWorkTelegramBot.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

/**
 * Конфигурационный класс для инициализации бота для работы с Telegram API.
 * Данный класс выполняет инициализацию и регистрацию бота при старте приложения.
 */
@Slf4j
@Component
public class BotInitializer {
    @Autowired
    TelegramBotService bot;

    /**
     * Метод выполняет инициализацию и регистрацию бота на сервере Telegram при помощи Telegram API.
     *
     * @throws TelegramApiException обрабатывает возможные исключения, связанные с регистрацией.
     */
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
