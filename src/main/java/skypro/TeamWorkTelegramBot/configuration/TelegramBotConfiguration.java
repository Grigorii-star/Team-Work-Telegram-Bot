package skypro.TeamWorkTelegramBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Класс для передечи из application.properties токена и имени Telegram бота.
 */
@Data
@PropertySource("application.properties")
@Configuration
@EnableScheduling
public class TelegramBotConfiguration{

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.name}")
    private String name;
}
