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

import java.util.ArrayList;
import java.util.List;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.INTERRUPT_CHAT_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.CHAT;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;


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

    List<Volunteer> notBusyVolunteers = new ArrayList<>();

    public CallVolunteer(SendMessageService sendMessageService,
                         AnimalOwnerRepository animalOwnerRepository,
                         VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
    }

    /**
     * Метод находит в БД свободного волонтера и соединяет с ним пользователя.
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

        /**
         * Чтобы не заходить каждый раз в базу, проверяем наш список, есть ли там волонтеры
         */
        if (notBusyVolunteers.isEmpty()) {

            /**
             * Если список пустой, то подгружаем свободных волонтеров из базы
             */
            notBusyVolunteers = volunteersRepository.findVolunteersByIsBusy(false);

            if (notBusyVolunteers.isEmpty()) {
                /**
                 * Если после выгрузки из базы список все равно пустой, то говорим, что сейчас нет свободных волонтеров
                 */
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        All_VOLUNTEERS_ARE_BUSY_MESSAGE,
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
            } else {
                /**
                 * Если после выгрузки из базы волонтеры появились в списке, то отрабатываем логику класса
                 */
                setVolunteerToUser(callbackQuery, telegramBotService, animalOwner);
            }
        } else {
            /**
             * Если волонтеры все еще есть в списке, то отрабатываем логику класса
             */
            setVolunteerToUser(callbackQuery, telegramBotService, animalOwner);

        }
    }

    /**
     * Метод соединяет пользователя c волонтером.<br>
     * Метод назначает пользователю AnimalOwner, boolean значение HelpVolunteer(true).<br>
     * Метод назначает пользователю AnimalOwner, boolean значение InChat(true).<br>
     * Метод присваивает пользователю AnimalOwner, свободного волонтера Volunteer(volunteer).<br>
     * Метод присваивает волонтеру AnimalOwner, boolean значение InChat(true).<br>
     * Метод назначает волонтеру Volunteer, boolean значение IsBusy(true).<br>
     * Метод присваивает волонтеру Volunteer, пользователя AnimalOwner(animalOwner).<br>
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @param animalOwner - найденный в БД пользователь.
     *
     * @see SendMessageService
     * @see Volunteer
     */
    private void setVolunteerToUser(CallbackQuery callbackQuery, TelegramBotService telegramBotService, AnimalOwner animalOwner) {
        Volunteer volunteer = notBusyVolunteers.get(0);
        notBusyVolunteers.remove(0);

        AnimalOwner volunteerOwner = animalOwnerRepository.findByIdChat(volunteer.getIdChat());

        animalOwner.setHelpVolunteer(true);
        animalOwner.setInChat(true);
        animalOwner.setVolunteer(volunteer);

        volunteerOwner.setInChat(true);

        volunteer.setIsBusy(true);
        volunteer.setAnimalOwner(animalOwner);

        animalOwnerRepository.save(volunteerOwner);

        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);

        sendMessageService.SendMessageToUserWithButtons(
                String.valueOf(callbackQuery.getFrom().getId()),
                CALL_VOLUNTEER_TO_USER_MESSAGE,
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
        sendMessageService.SendMessageToUserWithButtons(
                String.valueOf(volunteer.getIdChat()),
                CALL_VOLUNTEER_TO_VOLUNTEER_MESSAGE,
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
    }
}
