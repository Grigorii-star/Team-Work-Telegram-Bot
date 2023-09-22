package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
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
@Slf4j
@Component
public class CallVolunteer extends CommandAbstractClass {
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
     * @param callbackQuery      объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
        Volunteer volunteer = volunteersRepository.findDistinctFirstByIsBusy(false);

        AnimalOwner volunteerOwner = animalOwnerRepository.findByIdChat(volunteer.getIdChat());
        animalOwner.setHelpVolunteer(true);
        animalOwner.setInChat(true);
        animalOwner.setVolunteer(volunteer); // устанавливаем его волонтера

        volunteerOwner.setInChat(true);

        volunteer.setIsBusy(true); // волонтеру ставим, что занят
        volunteer.setAnimalOwner(animalOwner); // волонтеру ставим его владельца

        animalOwnerRepository.save(volunteerOwner);
        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);

        sendMessageService.SendMessageToUserWithButtons( //логика по авзову волонтёра// вызывается
                String.valueOf(callbackQuery.getFrom().getId()),
                "Напиши свой вопрос волонтёру, и он в ближайшее время тебе ответит.",
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
//        animalOwner.setHelpVolunteer(true);
//        animalOwnerRepository.save(animalOwner);
        sendMessageService.SendMessageToUserWithButtons( //логика по авзову волонтёра// вызывается
                String.valueOf(volunteer.getIdChat()),
                "Сейчас с тобой свяжется пользователь.",
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
    }
}
