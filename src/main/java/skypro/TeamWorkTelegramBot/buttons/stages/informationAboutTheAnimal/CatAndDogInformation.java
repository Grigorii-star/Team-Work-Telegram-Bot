package skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal;

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

@Component
public class CatAndDogInformation implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private TelegramBotService telegramBotService;

    public CatAndDogInformation(SendMessageService sendMessageService,
                                AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();
        String callData = update.getCallbackQuery().getData();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        switch (callData) {
            case "о_приюте":
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage1/dog-shelter-info.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage1/cat-shelter-info.txt"),
                            telegramBotService
                    );
                }
                break;
            case "расписание":
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage1/dog-schedule-address.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage1/cat-schedule-address.txt"),
                            telegramBotService
                    );
                }
                break;
            case "охрана":
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage1/dog-security-phone.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage1/cat-security-phone.txt"),
                            telegramBotService
                    );
                }
                break;
            case "техника_безопасности":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage1/safety_rules.txt"),
                        telegramBotService
                );
                break;
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
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}
