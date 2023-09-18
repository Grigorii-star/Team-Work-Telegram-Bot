package skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.GREETING_MESSAGE;


/**
 * Класс, который нужен для формирования кнопок для раздела информации по приюту
 */
@Component
public class Information extends CommandAbstractClass {
    private final SendMessageService sendMessageService;

    String[] buttonsText = {
            GET_MORE_INFO_SHELTER_BUTTON,
            SCHEDULE_BUTTON,
            SECURITY_BUTTON,
            SAFETY_PRECAUTIONS_BUTTON,
            POST_CONTACT_BUTTON,
            CALL_VOLUNTEER_BUTTON,
            MENU_BUTTON};
    String[] buttonsCallData = {
            ABOUT_SHELTER,
            SCHEDULE,
            SECURITY,
            SAFETY_PRECAUTIONS,
            POST_CONTACT,
            CALL_VOLUNTEER,
            MENU};

    public Information(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Содержит id пользователя и сообщение для пользователя,
     * отправляет сообщение, полученное из текстового файла,
     * и необходимые кнопки для пользователя
     * @param callbackQuery - объект телеграмма для получения значений из телеграмм бота
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        Long chatId = callbackQuery.getFrom().getId();

        sendMessageService.SendMessageToUserWithButtons(
                String.valueOf(chatId),
                GREETING_MESSAGE,
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
    }
}
