package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.INTERRUPT_CHAT_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.CHAT;


/**
 * Класс соединяет пользователя с волонтером.
 */
@Slf4j
@Component
public class CallVolunteer extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;
    String[] buttonsText = {INTERRUPT_CHAT_BUTTON};
    String[] buttonsCallData = {CHAT};

    public CallVolunteer(SendMessageService sendMessageService,
                         AnimalOwnerRepository animalOwnerRepository,
                         VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
    }

    /**
     * Метод находит в БД свободного волонтера и соединяет с ним пользователя.
     * Метод назначает пользователю AnimalOwner, boolean значение HelpVolunteer(true).
     * Метод назначает пользователю AnimalOwner, boolean значение InChat(true).
     * Метод присваивает пользователю AnimalOwner, свободного волонтера Volunteer(volunteer).
     * Метод назначает волонтеру Volunteer, boolean значение IsBusy(true).
     * Метод присваивает волонтеру Volunteer, пользователя AnimalOwner(animalOwner).
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     * @see AnimalOwner
     * @see Volunteer
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());

        // todo сделать проверку на null. (складывать волонтеров в лист?)
        Volunteer volunteer = volunteersRepository.findDistinctFirstByIsBusy(false);

        animalOwner.setHelpVolunteer(true);
        animalOwner.setInChat(true);
        animalOwner.setVolunteer(volunteer);

        volunteer.setIsBusy(true);
        volunteer.setAnimalOwner(animalOwner);

        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);

        sendMessageService.SendMessageToUserWithButtons(
                String.valueOf(callbackQuery.getFrom().getId()),
                "Напиши свой вопрос волонтёру, и он в ближайшее время тебе ответит.", // todo вынести в константу
                buttonsText,
                buttonsCallData,
                telegramBotService
        );

        sendMessageService.SendMessageToUserWithButtons(
                String.valueOf(volunteer.getIdChat()),
                "Сейчас с тобой свяжется пользователь.", // todo вынести в константу
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
    }
}
