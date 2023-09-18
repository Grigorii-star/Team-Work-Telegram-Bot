package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

/**
 * Класс для сохранения отчета о животном.
 */
@Slf4j
@Component
public class CanSaveReportAboutPet extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public CanSaveReportAboutPet(SendMessageService sendMessageService,
                                 AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Этот метод Вытягивает chatId из update с помощью getCallbackQuery().getFrom().getId().
     * Вызывает метод sendMessageService.SendMessageToUser() и передает в него chatId
     *
     * @param callbackQuery             объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        log.info("Invoked a method for save animal report");

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
        animalOwner.setCanSendReport(true);
        animalOwnerRepository.save(animalOwner);

        sendMessageService.SendMessageToUser(
                String.valueOf(callbackQuery.getFrom().getId()),
                "Отправьте фото с прикрепленным текстовым отчетом. \n" +
                        "Отчет должен содержать: \n" +
                        "Рацион животного. \n" +
                        "Общее самочувствие и привыкание к новому месту. \n" +
                        "Изменение в поведении: отказ от старых привычек, приобретение новых. ",
                telegramBotService
        );
    }
}
