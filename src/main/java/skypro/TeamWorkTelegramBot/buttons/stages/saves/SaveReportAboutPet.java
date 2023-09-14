package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

/**
 * Класс для сохранения отчета о живаотном.
 */
@Component
public class SaveReportAboutPet implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public SaveReportAboutPet(SendMessageService sendMessageService,
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
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();

        sendMessageService.SendMessageToUser(
                String.valueOf(chatId),
                "Здесь должна быть логика сохранению отчета от владельца животного",
                telegramBotService
        );
    }
}
