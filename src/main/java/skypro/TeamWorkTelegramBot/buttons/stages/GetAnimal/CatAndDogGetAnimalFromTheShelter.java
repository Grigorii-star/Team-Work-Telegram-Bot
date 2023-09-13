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

@Component
public class CatAndDogGetAnimalFromTheShelter implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private TelegramBotService telegramBotService;

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
            case "правила_знакомства_собака":
            case "правила_знакомства_кошка":
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
            case "список_документов":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/doc-list.txt"),
                        telegramBotService
                );
                break;
            case "транспортировка":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/transfer.txt"),
                        telegramBotService
                );
                break;
            case "дом_для_щенка":
            case "дом_для_котенка":
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
            case "дом_для_животного":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/adult-pet-house.txt"),
                        telegramBotService
                );
                break;
            case "дом_для_инвалида":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage2/invalid-adult-pet-house.txt"),
                        telegramBotService
                );
                break;
            case "советы_кинолога":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        "Здесь должны быть советы кинолога по первичному общению с собакой",
                        telegramBotService
                );
                break;
            case "контакты_кинолога":
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        "Здесь должны быть контактные данные проверенных кинологов",
                        telegramBotService
                );
                break;
            case "причина_отказа":
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
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}
