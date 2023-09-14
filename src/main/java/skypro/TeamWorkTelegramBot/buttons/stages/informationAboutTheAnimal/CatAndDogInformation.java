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

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

@Component
public class CatAndDogInformation implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

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
            case ABOUT_SHELTER:
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
            case SCHEDULE:
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
            case SECURITY:
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
            case SAFETY_PRECAUTIONS:
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        getInfo("src/main/resources/bot-files/stage1/safety_rules.txt"),
                        telegramBotService
                );
                break;
        }
    }

}
