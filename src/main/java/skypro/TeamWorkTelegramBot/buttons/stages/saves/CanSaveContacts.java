package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.MENU_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.MENU;

/**
 * Класс, для сохранению контактов пользователя в базу данных
 */
@Slf4j
@Component
public class CanSaveContacts extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public final static String GREETING_MESSAGE = "Введи свои контактные данные в формате:\n" +
            "+7 999 999 99 99";

    public CanSaveContacts(SendMessageService sendMessageService,
                           AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Этот метод Вытягивает chatId из update с помощью getCallbackQuery().getFrom().getId().
     * Вызывает метод sendMessageService.SendMessageToUser() и передает в него chatId
     *
     * @param callbackQuery      объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {

        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
        checkAnimalOwner.setCanSaveContact(true);
        animalOwnerRepository.save(checkAnimalOwner);

        sendMessageService.SendMessageToUser(
                String.valueOf(callbackQuery.getFrom().getId()),
                GREETING_MESSAGE,
                telegramBotService
        );
    }
}
