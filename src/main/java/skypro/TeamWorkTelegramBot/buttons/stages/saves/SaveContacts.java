package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.MENU_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.MENU;

@Slf4j
@Component
public class SaveContacts extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public final static String GREETING_MESSAGE_OK = "Отлично! Я сохранил твои контактные данные.\n" +
            "Для перехода в главное меню нажми на кнопку ниже.";
    public final static String GREETING_MESSAGE_OK2 = "Отлично! Я сохранил твои контактные данные.\n" +
            "Для перехода в меню выбора приюта нажми на кнопку ниже /start";

    String[] buttonsText = {MENU_BUTTON};
    String[] buttonsCallData = {MENU};

    public SaveContacts(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    @Override
    public void messagesExtractor(Message message, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(message.getChatId());

        if (message.hasText() && animalOwner.getCanSaveContact() && !animalOwner.getIsVolunteer()) {

            animalOwner.setContactInformation(String.valueOf(message.getText()));
            animalOwner.setCanSaveContact(false);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(message.getChatId()),
                    GREETING_MESSAGE_OK,
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
                    GREETING_MESSAGE_OK2,
                    telegramBotService
            );
        }
    }
}
