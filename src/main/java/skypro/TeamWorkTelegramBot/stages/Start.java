package skypro.TeamWorkTelegramBot.stages;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.service.SendMessageService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class Start implements Command {
    private final SendMessageService sendMessageService;
    public final static String GREETING_MESSAGE = "Привет! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
            "Я отвечу на все вопросы и помогу определиться с выбором.";

    String[] buttonsText = {"Приют для собак",
                            "Приют для кошек"};
    String[] buttonsCallData = {"собака",
                                "кошка"};

    public Start(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        sendMessageService.SendMessageToUser(String.valueOf(chatId), GREETING_MESSAGE);
        sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"), buttonsText, buttonsCallData);
    }

    private String getInfo(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}
