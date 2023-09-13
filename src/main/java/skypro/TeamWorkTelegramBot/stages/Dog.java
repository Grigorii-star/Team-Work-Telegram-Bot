package skypro.TeamWorkTelegramBot.stages;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.service.SendMessageService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс, который нужен для формирования ответа пользователю
 */
@Component
public class Dog implements Command {
    private final SendMessageService sendMessageService;
    public final static String GREETING_MESSAGE = "Привет! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
            "Я отвечу на все вопросы и помогу определиться с выбором.";

    String[] buttonsText = {"Приют для собак",
                            "Приют для кошек"};
    String[] buttonsCallData = {"собака",
                                "кошка"};

    public Dog(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Содержит id пользователя и сообщение для пользователя,
     * отправляет сообщение, полученное из текстового файла,
     * и необходимые кнопки для пользователя
     * @param update - id пользователя
     */
    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        sendMessageService.SendMessageToUser(String.valueOf(chatId), GREETING_MESSAGE);
        sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"), buttonsText, buttonsCallData);
    }

    /**
     * Метод, который нужен для обработки текстовых файлов в String
     * @param filePath - принимает текстовый файл
     * @return String message
     * @throws IOException
     */
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
