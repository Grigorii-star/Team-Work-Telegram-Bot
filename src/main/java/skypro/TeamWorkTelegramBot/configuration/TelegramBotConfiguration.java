package skypro.TeamWorkTelegramBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * класс, для передечи из application.properties токена и имени телеграмм бота
 */
@Configuration
@Data
@PropertySource("application.properties")
public class TelegramBotConfiguration{
    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.name}")
    private String name;

}
