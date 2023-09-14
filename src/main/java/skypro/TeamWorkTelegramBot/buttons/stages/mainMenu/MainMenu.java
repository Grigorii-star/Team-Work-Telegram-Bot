package skypro.TeamWorkTelegramBot.buttons.stages.mainMenu;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.GREETING_MESSAGE;

/**
 * Класс, который нужен для формирования ответа пользователю
 */
@Component
public class MainMenu implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;


    String[] buttonsText = {GET_INFO_SHELTER_BUTTON,
                            GET_PET_BUTTON,
                            REPORT_BUTTON,
                            VOLUNTEER_BUTTON};
    String[] buttonsCallData = {INFO,
                                GET_AN_ANIMAL,
                                REPORT,
                                VOLUNTEER};

    public MainMenu(SendMessageService sendMessageService,
                    AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();
        String callData = update.getCallbackQuery().getData();

        if (callData.equals(DOG)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
            animalOwner.setDogLover(true);
            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (callData.equals(CAT)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
            animalOwner.setDogLover(false);
            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (callData.equals(MENU)) {
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
    }

}

