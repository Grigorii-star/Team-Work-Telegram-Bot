package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

/**
 * Класс для сохранения отчета о животном.
 */
@Slf4j
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
     *
     * @param update             объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        log.info("Invoked a method for save animal report");

        Long userChatIdQuery = update.getCallbackQuery().getFrom().getId();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(userChatIdQuery);
        animalOwner.setCanSendReport(true);
        animalOwnerRepository.save(animalOwner);

        sendMessageService.SendMessageToUser(
                String.valueOf(userChatIdQuery),
                "Отправьте фото с прикрепленным текстовым отчетом. \n" +
                        "Отчет должен содержать: \n" +
                        "Рацион животного. \n" +
                        "Общее самочувствие и привыкание к новому месту. \n" +
                        "Изменение в поведении: отказ от старых привычек, приобретение новых. ",
                telegramBotService
        );
    }
}
