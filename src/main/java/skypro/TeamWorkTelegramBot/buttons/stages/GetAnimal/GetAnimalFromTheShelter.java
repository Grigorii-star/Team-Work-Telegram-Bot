package skypro.TeamWorkTelegramBot.buttons.stages.GetAnimal;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс, для создания кнопок меню информацию по приюту в телеграмм
 */
@Component
public class GetAnimalFromTheShelter implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public GetAnimalFromTheShelter(SendMessageService sendMessageService,
                                   AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    String[] buttonsTextDog = {
            MEETING_DOG_RULES_BUTTON,
            DOC_LIST_DOG_BUTTON,
            TRANSPORTATION_DOG_BUTTON,
            PUPPY_HOUSE_BUTTON,
            PET_HOUSE_BUTTON,
            INVALID_HOUSE_BUTTON,
            DOG_HANDLER_ADVICE_BUTTON,
            DOG_HANDLER_CONTACTS_BUTTON,
            REFUSAL_REASONS_DOG_BUTTON,
            POST_CONTACT_BUTTON,
            CALL_VOLUNTEER_BUTTON,
            MENU_BUTTON};

    String[] buttonsCallDataDog = {
            MEETING_DOG_RULES,
            DOC_LIST,
            TRANSPORTATION,
            PUPPY_HOUSE,
            PET_HOUSE,
            INVALID_HOUSE,
            DOG_HANDLER_ADVICE,
            DOG_HANDLER_CONTACTS,
            REFUSAL_REASONS,
            POST_CONTACT,
            CALL_VOLUNTEER,
            MENU};

    String[] buttonsTextCat = {
            MEETING_CAT_RULES_BUTTON,
            DOC_LIST_CAT_BUTTON,
            TRANSPORTATION_CAT_BUTTON,
            PUSSY_HOUSE_BUTTON,
            PET_HOUSE_BUTTON,
            INVALID_HOUSE_BUTTON,
            REFUSAL_REASONS_CAT_BUTTON,
            POST_CONTACT_BUTTON,
            CALL_VOLUNTEER_BUTTON,
            MENU_BUTTON};
    String[] buttonsCallDataCat = {
            MEETING_CAT_RULES,
            DOC_LIST,
            TRANSPORTATION,
            PUSSY_HOUSE,
            PET_HOUSE,
            INVALID_HOUSE,
            REFUSAL_REASONS,
            POST_CONTACT,
            CALL_VOLUNTEER,
            MENU};

    /**
     * данный метод отправляет приветственное сообщение с кнопками в зависимости от того, является
     * ли пользователь любителем собак или кошек, полученных в параметре Update.
     * @param update объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        if (animalOwner.getDogLover()) {
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsTextDog,
                    buttonsCallDataDog,
                    telegramBotService
            );
        }
        else {
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsTextCat,
                    buttonsCallDataCat,
                    telegramBotService
            );
        }
    }
}
