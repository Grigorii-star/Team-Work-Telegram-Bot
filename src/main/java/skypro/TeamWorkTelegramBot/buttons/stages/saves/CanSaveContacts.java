package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.SAVE_CONTACT_MESSAGE;

/**
 * Класс предоставляет шаблон заполнения для сохранения контакта пользователя.
 */
@Slf4j
@Component
public class CanSaveContacts extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public CanSaveContacts(SendMessageService sendMessageService,
                           AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод формирует шаблон заполнения контакта и отправляет его пользователю.<br>
     * Метод назначает пользователю AnimalOwner, boolean значение CanSaveContact(true).
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     * @see AnimalOwner
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {

        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
        checkAnimalOwner.setCanSaveContact(true);
        animalOwnerRepository.save(checkAnimalOwner);

        sendMessageService.SendMessageToUser(
                String.valueOf(callbackQuery.getFrom().getId()),
                SAVE_CONTACT_MESSAGE,
                telegramBotService
        );
    }
}
