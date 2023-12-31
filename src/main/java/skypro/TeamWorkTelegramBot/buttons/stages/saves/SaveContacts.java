package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.MENU_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.MENU;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.SAVE_CONTACT_WITH_MENU_SUCCESSFULLY_MESSAGE;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.SAVE_CONTACT_WITH_START_SUCCESSFULLY_MESSAGE;

/**
 * Класс сохраняет контакт пользователя в БД.
 */
@Slf4j
@Component
public class SaveContacts extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    String[] buttonsText = {MENU_BUTTON};
    String[] buttonsCallData = {MENU};

    public SaveContacts(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод сохраняет в БД контактный телефон пользователя или волонтера.<br>
     * Метод назначает пользователю AnimalOwner, boolean значение CanSaveContact(false).
     *
     * @param message - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     */
    @Override
    public void messagesExtractor(Message message, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(message.getChatId());

        if (message.hasText() && animalOwner.getCanSaveContact() && !animalOwner.getIsVolunteer()) {

            animalOwner.setContactInformation(String.valueOf(message.getText()));
            animalOwner.setCanSaveContact(false);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(message.getChatId()),
                    SAVE_CONTACT_WITH_MENU_SUCCESSFULLY_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (message.hasText() && animalOwner.getCanSaveContact() && animalOwner.getIsVolunteer()) {

            animalOwner.setContactInformation(String.valueOf(message.getText()));
            animalOwner.setCanSaveContact(false);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUser(
                    String.valueOf(message.getChatId()),
                    SAVE_CONTACT_WITH_START_SUCCESSFULLY_MESSAGE,
                    telegramBotService
            );
        }
    }
}
