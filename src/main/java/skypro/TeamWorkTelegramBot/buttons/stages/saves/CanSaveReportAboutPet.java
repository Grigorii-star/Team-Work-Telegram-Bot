package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

/**
 * Класс предоставляет шаблон заполнения для сохранения отчета о животном.
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
     * Метод формирует шаблон заполнения для сохранения отчета о животном и отправляет его пользователю.<br>
     * Метод назначает пользователю AnimalOwner, boolean значение CanSendReport(true).
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     * @see AnimalOwner
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        log.info("Invoked a method for save animal report");

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
        animalOwner.setCanSendReport(true);
        animalOwnerRepository.save(animalOwner);

        sendMessageService.SendMessageToUser(
                String.valueOf(callbackQuery.getFrom().getId()),
                "Отправьте фото с прикрепленным текстовым отчетом. \n" +  // todo вынести в константу
                        "Отчет должен содержать: \n" +
                        "Рацион животного. \n" +
                        "Общее самочувствие и привыкание к новому месту. \n" +
                        "Изменение в поведении: отказ от старых привычек, приобретение новых. ",
                telegramBotService
        );
    }
}
