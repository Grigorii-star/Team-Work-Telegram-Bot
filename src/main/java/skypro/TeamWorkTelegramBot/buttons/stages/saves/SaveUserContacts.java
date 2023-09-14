package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.UpdatesReader;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

@Component
public class SaveUserContacts implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private TelegramBotService telegramBotService;

    public final static String GREETING_MESSAGE = "Введи свои контактные данные в формате:\n" +
            "+79261234567";
    public final static String GREETING_MESSAGE_OK = "Отлично! Я сохранил твои контактные данные.\n" +
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
        if (update.hasCallbackQuery()) {

            Long chatIdQuery = update.getCallbackQuery().getFrom().getId();
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatIdQuery),
                    GREETING_MESSAGE,
                    telegramBotService
            );
        }

        else {
            Long chatId = update.getMessage().getChatId();
            String contactInformationText = update.getMessage().getText();

            AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);

            checkAnimalOwner.setContactInformation(String.valueOf(contactInformationText)); //number или contactInformationText
            animalOwnerRepository.save(checkAnimalOwner);

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
