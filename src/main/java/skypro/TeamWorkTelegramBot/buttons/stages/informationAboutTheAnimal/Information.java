package skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;


/**
 * Класс, который нужен для формирования ответа пользователю
 */
@Component
public class Information implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    public final static String GREETING_MESSAGE = "Молодец, что перешёл в меню консультаций! " +
            "\nДля продолжения, пожалуйста, ознакомься с информацией о приюте.";

    String[] buttonsText = {
            "Узнать подробнее о приюте",
            "Узнать расписание работы, адрес и схему проезда",
            "Получить телефон охраны для оформления пропуска на машину",
            "Получить общие рекомендации по технике безопасности на территории приюта",
            "Оставить контактные данные, чтобы волонтер мог связаться",
            "Позвать волонтера",
            "Перейти в главное меню"};
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
     *
     * @param update - id пользователя
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
