package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;


/**
 * Класс, для общения с волонтером
 */
@Component
public class CallVolunteer implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public CallVolunteer(SendMessageService sendMessageService,
                         AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    String[] buttonsText = {"Прервать чат"};
    String[] buttonsCallData = {"чат"};

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Этот метод Вытягивает chatId из update с помощью getCallbackQuery().getFrom().getId().
     * Вызывает метод sendMessageService.SendMessageToUser() и передает в него chatId
     *
     * @param update             объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        if (!animalOwner.getBeVolunteer()) {

            animalOwner.setHelpVolunteer(true);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUser( //логика по авзову волонтёра// вызывается
                    String.valueOf(chatId),
                    "Напиши свой вопрос волонтёру, и он в ближайшее время тебе ответит.",
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
    }
}
