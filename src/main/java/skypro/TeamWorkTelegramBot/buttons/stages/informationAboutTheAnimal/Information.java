package skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.GREETING_MESSAGE;


/**
 * Класс, который нужен для формирования кнопок для раздела информации по приюту
 */
@Component
public class Information implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    String[] buttonsText = {
            GET_MORE_INFO_SHELTER_BUTTON,
            SCHEDULE_BUTTON,
            SECURITY_BUTTON,
            SAFETY_PRECAUTIONS_BUTTON,
            POST_CONTACT_BUTTON,
            VOLUNTEER_BUTTON,
            MENU_BUTTON};
    String[] buttonsCallData = {
            ABOUT_SHELTER,
            SCHEDULE,
            SECURITY,
            SAFETY_PRECAUTIONS,
            POST_CONTACT,
            VOLUNTEER,
            MENU};

    public Information(SendMessageService sendMessageService,
                       AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Содержит id пользователя и сообщение для пользователя,
     * отправляет сообщение, полученное из текстового файла,
     * и необходимые кнопки для пользователя
     * @param update - объект телеграмма для получения значений из телеграмм бота
     */
    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();

        sendMessageService.SendMessageToUser(
                String.valueOf(chatId),
                GREETING_MESSAGE,
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
    }
}
