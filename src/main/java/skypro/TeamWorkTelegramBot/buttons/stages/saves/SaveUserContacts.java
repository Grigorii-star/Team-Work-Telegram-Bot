package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

@Component
@Slf4j
public class SaveUserContacts implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private TelegramBotService telegramBotService;

    public final static String GREETING_MESSAGE = "Введи свои контактные данные в формате:\n" +
            "+7 999 999 99 99";
    public final static String GREETING_MESSAGE_OK = "Отлично! Я сохранил твои контактные данные.\n" +
            "Для перехода в главное меню нажми на кнопку ниже.";
    public final static String GREETING_MESSAGE_NO = "У меня уже есть твои контактные данные.\n" +
            "Для перехода в главное меню нажми на кнопку ниже.";


    String[] buttonsText = {
            "Перейти в главное меню"};
    String[] buttonsCallData = {
            "меню"};

    public SaveUserContacts(SendMessageService sendMessageService,
                            AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
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

        } else if (update.getMessage().hasText() && animalOwner.getCanSaveContact()) {
            Long chatId = update.getMessage().getChatId();
            String contactInformationText = update.getMessage().getText();

            animalOwner.setContactInformation(String.valueOf(contactInformationText));
            animalOwner.setCanSaveContact(false);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE_OK,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
    }
}
