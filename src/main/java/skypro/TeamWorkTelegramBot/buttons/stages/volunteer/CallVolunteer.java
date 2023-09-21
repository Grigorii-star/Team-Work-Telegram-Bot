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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

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

    List<Volunteer> notBusyVolunteers = new ArrayList<>();


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
                sendMessageService.SendMessageToUserWithButtons( //логика по авзову волонтёра// вызывается
                        String.valueOf(callbackQuery.getFrom().getId()),
                        "Сейчас все волонтеры заняты, попробуй позже",
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

    private void setVolunteerToUser(CallbackQuery callbackQuery, TelegramBotService telegramBotService, AnimalOwner animalOwner) {
        Volunteer volunteer = notBusyVolunteers.get(0);
        notBusyVolunteers.remove(0);
        animalOwner.setHelpVolunteer(true);
        animalOwner.setInChat(true);
        animalOwner.setVolunteer(volunteer); // устанавливаем его волонтера
        volunteer.setIsBusy(true); // волонтеру ставим, что занят
        volunteer.setAnimalOwner(animalOwner); // волонтеру ставим его владельца
        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);

        sendMessageService.SendMessageToUserWithButtons( //логика по авзову волонтёра// вызывается
                String.valueOf(callbackQuery.getFrom().getId()),
                "Напиши свой вопрос волонтёру, и он в ближайшее время тебе ответит.",
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
        sendMessageService.SendMessageToUserWithButtons( //логика по авзову волонтёра// вызывается
                String.valueOf(volunteer.getIdChat()),
                "Сейчас с тобой свяжется пользователь.",
                buttonsText,
                buttonsCallData,
                telegramBotService
        );
    }
}
