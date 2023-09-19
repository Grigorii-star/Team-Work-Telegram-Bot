package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.stages.saves.CanSaveContacts.GREETING_MESSAGE;

/**
 * Класс предоставляет шаблон заполнения для сохранения контакта волонтера.
 */
@Component
public class BecomeVolunteer extends CommandAbstractClass {

    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public BecomeVolunteer(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод формирует шаблон заполнения контакта и отправляет его пользователю.<br>
     * Метод назначает пользователю AnimalOwner, boolean значение CanSaveContact(true).
     * Метод назначает пользователю AnimalOwner, boolean значение IsVolunteer(true).
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     * @see AnimalOwner
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
        if (!animalOwner.getIsVolunteer() && !animalOwner.getInChat()) {

            animalOwner.setIsVolunteer(true);
            animalOwner.setCanSaveContact(true);

            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(callbackQuery.getFrom().getId()),
                    "Спасибо за твою готовность помогать!\n" + // todo вынести в константу
                            GREETING_MESSAGE,
                    telegramBotService
            );
        }
    }
}
