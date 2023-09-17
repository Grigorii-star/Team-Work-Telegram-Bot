package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.INTERRUPT_CHAT_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.CHAT;


/**
 * Класс, для общения с волонтером
 */
@Component
public class CallVolunteer implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;

    public CallVolunteer(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository, VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;

        this.volunteersRepository = volunteersRepository;
    }

    String[] buttonsText = {INTERRUPT_CHAT_BUTTON};
    String[] buttonsCallData = {CHAT};

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

        //Long volunteerId = getChatIdVolunteer();
        Volunteer volunteer = volunteersRepository.findDistinctFirstByIsBusy(false);
//        animalOwner.setHelpVolunteer(true); //устанавливаем что владельцу сейчас помогает волонтер
        animalOwner.setVolunteer(volunteer); // устанавливаем его волонтера
        volunteer.setIsBusy(true); // волонтеру ставим, что занят
        volunteer.setAnimalOwner(animalOwner); // волонтеру ставим его владельца
        //сохраняем в базу данных все
        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);

        //если н волонтер
//        if (!animalOwner.getBeVolunteer()) {

            animalOwner.setHelpVolunteer(true);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUser( //логика по авзову волонтёра// вызывается
                    String.valueOf(chatId), "Напиши свой вопрос волонтёру, и он в ближайшее время тебе ответит.", buttonsText, buttonsCallData, telegramBotService);
//        } else if (animalOwner.getBeVolunteer()) {

            animalOwner.setHelpVolunteer(true);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUser( //логика по авзову волонтёра// вызывается
                    String.valueOf(volunteer.getIdChat()), "Сейчас с тобой свяжется пользователь.", buttonsText, buttonsCallData, telegramBotService);
//        }
    }
}
