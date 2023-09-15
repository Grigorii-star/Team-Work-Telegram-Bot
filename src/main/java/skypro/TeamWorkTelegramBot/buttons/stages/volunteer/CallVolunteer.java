package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.stages.saves.SaveUserContacts.GREETING_MESSAGE;


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
            animalOwner.setCanSaveContact(true);

            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    "Спасибо за твою готовность помогать!\n" +
                            GREETING_MESSAGE,
                    telegramBotService
            );
        }
         else if (!animalOwner.getBeVolunteer() && animalOwner.getDogLover()) {

            sendMessageService.SendMessageToUser( //логика по авзову волонтёра// вызывается
                    String.valueOf(chatId),
                    "Напиши свой вопрос волонтёру, и он в ближайшее время тебе ответит.",
                    telegramBotService
            );
        }
        else if (animalOwner.getTookTheAnimal()) { //заменить на help_volunteer


            sendMessageService.SendMessageToUser( //логика по авзову волонтёра
                    String.valueOf(chatId),
                    "ответ волонтёра",
                    telegramBotService
            );
        }
    }
}
