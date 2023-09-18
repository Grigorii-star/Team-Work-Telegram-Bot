package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
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
public class SaveUserContacts extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public final static String GREETING_MESSAGE = "Введи свои контактные данные в формате:\n" +
            "+7 999 999 99 99";
    public final static String GREETING_MESSAGE_OK = "Отлично! Я сохранил твои контактные данные.\n" +
            "Для перехода в главное меню нажми на кнопку ниже.";
    public final static String GREETING_MESSAGE_OK2 = "Отлично! Я сохранил твои контактные данные.\n" +
            "Для перехода в меню выбора приюта нажми на кнопку ниже /start";


    String[] buttonsText = {MENU_BUTTON};
    String[] buttonsCallData = {MENU};

    public SaveUserContacts(SendMessageService sendMessageService,
                            AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }
    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Этот метод Вытягивает chatId из update с помощью getCallbackQuery().getFrom().getId().
     * Вызывает метод sendMessageService.SendMessageToUser() и передает в него chatId
     * @param update объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void updatesExtractor(Update update, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = new AnimalOwner();

        try {
            animalOwner = animalOwnerRepository.findByIdChat(update.getMessage().getChatId());
        } catch (NullPointerException e) {
            log.error("Error NullPointerException по update.getMessage().getChatId()");
        }

        if (update.hasCallbackQuery()) {
            Long chatIdQuery = update.getCallbackQuery().getFrom().getId();

            AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatIdQuery);
            checkAnimalOwner.setCanSaveContact(true);
            animalOwnerRepository.save(checkAnimalOwner);

            sendMessageService.SendMessageToUser(
                    String.valueOf(chatIdQuery),
                    GREETING_MESSAGE,
                    telegramBotService
            );

        } else if (update.getMessage().hasText() && animalOwner.getCanSaveContact() && !animalOwner.getBeVolunteer()) {
            Long chatId = update.getMessage().getChatId();
            String contactInformationText = update.getMessage().getText();

            animalOwner.setContactInformation(String.valueOf(contactInformationText));
            animalOwner.setCanSaveContact(false);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(chatId),
                    GREETING_MESSAGE_OK,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (update.getMessage().hasText() && animalOwner.getCanSaveContact() && animalOwner.getBeVolunteer()) {
            Long chatId = update.getMessage().getChatId();
            String contactInformationText = update.getMessage().getText();

            animalOwner.setContactInformation(String.valueOf(contactInformationText));
            animalOwner.setCanSaveContact(false);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE_OK2,
                    telegramBotService
            );
        }
    }
}
