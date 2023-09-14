package skypro.TeamWorkTelegramBot.buttons.stages.start;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс, который нужен для формирования ответа пользователю
 */
@Component
public class Start implements Command {
    private  final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    String[] buttonsText = {"Приют для собак",
                            "Приют для кошек"};
    String[] buttonsCallData = {DOG,
                                CAT};

    public Start(SendMessageService sendMessageService,
                 AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Содержит id пользователя и сообщение для пользователя,
     * отправляет сообщение, полученное из текстового файла,
     * и необходимые кнопки для пользователя
     * @param update - id пользователя
     */

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long textChatId = update.getMessage().getChatId();
        String start = update.getMessage().getText();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(textChatId);

        if (start.equals("/start") && animalOwner.getRegistered() == null) {
            animalOwner.setRegistered(true);
            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(String.valueOf(textChatId), GREETING_MESSAGE, telegramBotService);
            sendMessageService.SendMessageToUser(
                    String.valueOf(textChatId),
                    getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"),
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (start.equals("/start") && animalOwner.getRegistered()) {
            sendMessageService.SendMessageToUser(
                    String.valueOf(textChatId),
                    "Можете выбрать приют.",
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
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
                sb.append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}