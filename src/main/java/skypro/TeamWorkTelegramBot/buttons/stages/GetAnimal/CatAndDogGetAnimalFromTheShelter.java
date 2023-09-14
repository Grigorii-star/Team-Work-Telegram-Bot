package skypro.TeamWorkTelegramBot.buttons.stages.GetAnimal;

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

@Component
public class CatAndDogGetAnimalFromTheShelter implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public CatAndDogGetAnimalFromTheShelter(SendMessageService sendMessageService,
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
            case MEETING_DOG_RULES:
            case MEETING_CAT_RULES:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage2/dog-rules.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage2/cat-rules.txt"),
                            telegramBotService
                    );
                }
                break;
            case DOC_LIST:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/doc-list.txt"),
                        telegramBotService
                );
                break;
            case TRANSPORTATION:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/transfer.txt"),
                        telegramBotService
                );
                break;
            case PUPPY_HOUSE:
            case PUSSY_HOUSE:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage2/doggy-house.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(chatId),
                            getInfo("src/main/resources/bot-files/stage2/kitten-house.txt"),
                            telegramBotService
                    );
                }
                break;
            case PET_HOUSE:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/adult-pet-house.txt"),
                        telegramBotService
                );
                break;
            case INVALID_HOUSE:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/invalid-adult-pet-house.txt"),
                        telegramBotService
                );
                break;
            case DOG_HANDLER_ADVICE:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        "Здесь должны быть советы кинолога по первичному общению с собакой",
                        telegramBotService
                );
                break;
            case DOG_HANDLER_CONTACTS:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        "Здесь должны быть контактные данные проверенных кинологов",
                        telegramBotService
                );
                break;
            case REFUSAL_REASONS:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/refuse-reasons.txt"),
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
                sb.append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}
