package skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс, отвечающий за логику кнопок из класса information
 */
@Component
public class CatAndDogInformation extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public CatAndDogInformation(SendMessageService sendMessageService,
                                AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }
    /**
     * Данный метод получает данные из объекта update.
     * Используя полученный chatId, получается объект animalOwner из репозитория
     * animalOwnerRepository с использованием метода findByIdChat(chatId).Обрабатывает разные варианты
     * callData, отправляя разные сообщения пользователям в зависимости от выбранной опции.
     * @param callbackQuery объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());

        switch (callbackQuery.getData()) {
            case ABOUT_SHELTER:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage1/dog-shelter-info.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage1/cat-shelter-info.txt"),
                            telegramBotService
                    );
                }
                break;
            case SCHEDULE:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage1/dog-schedule-address.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage1/cat-schedule-address.txt"),
                            telegramBotService
                    );
                }
                break;
            case SECURITY:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage1/dog-security-phone.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage1/cat-security-phone.txt"),
                            telegramBotService
                    );
                }
                break;
            case SAFETY_PRECAUTIONS:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage1/safety_rules.txt"),
                        telegramBotService
                );
                break;
        }
    }
}
