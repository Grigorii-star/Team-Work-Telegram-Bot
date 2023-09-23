package skypro.TeamWorkTelegramBot.buttons.stages.menu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс формирует главное меню приюта.
 */
@Slf4j
@Component
public class MainMenu extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;
    private final SheltersRepository sheltersRepository;

    private final String DOG_SHELTER = "Dog";
    private final String CAT_SHELTER = "Cat";

    String[] buttonsText = {GET_INFO_SHELTER_BUTTON,
                            GET_PET_BUTTON,
                            REPORT_BUTTON,
                            CALL_VOLUNTEER_BUTTON};
    String[] buttonsCallData = {INFO,
                                GET_AN_ANIMAL,
                                REPORT,
                                CALL_VOLUNTEER};

    public MainMenu(SendMessageService sendMessageService,
                    AnimalOwnerRepository animalOwnerRepository,
                    VolunteersRepository volunteersRepository,
                    SheltersRepository sheltersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
        this.sheltersRepository = sheltersRepository;
    }

    /**
     * Метод обрабатывает разные варианты callbackQuery, устанавливает настройки "любителя собак" или
     * "любителя кошек" в зависимости от выбранной опции, сохраняет изменения в репозитории и отправляет
     * приветственное сообщение с кнопками пользователю.<br>
     * Метод позволяет перейти в главное меню из разных этапов.<br>
     * Метод позволяет разъеденить чат с волонтером и перевести пользователя в главное меню.
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     * @see CallbackQuery
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {

        if (callbackQuery.getData().equals(DOG)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
            Shelter shelter = sheltersRepository.findByName(DOG_SHELTER);
            animalOwner.setDogLover(true);
            animalOwner.setShelter(shelter);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(callbackQuery.getFrom().getId()),
                    START_GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (callbackQuery.getData().equals(CAT)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());
            Shelter shelter = sheltersRepository.findByName(CAT_SHELTER);
            animalOwner.setDogLover(false);
            animalOwner.setShelter(shelter);
            animalOwnerRepository.save(animalOwner);

            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(callbackQuery.getFrom().getId()),
                    START_GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (callbackQuery.getData().equals(MENU)) {
            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(callbackQuery.getFrom().getId()),
                    START_GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if (callbackQuery.getData().equals(CHAT)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());

            Volunteer volunteer;
            if (!animalOwner.getIsVolunteer()) {
                animalOwner.setInChat(false);
                animalOwnerRepository.save(animalOwner);

                volunteer = volunteersRepository.findByAnimalOwner(animalOwner);
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        INTERRUPT_CHAT_WITH_VOLUNTEER_MESSAGE,
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
                sendMessageService.SendMessageToUser(
                        String.valueOf(volunteer.getIdChat()),
                        INTERRUPT_CHAT_WITH_USER_MESSAGE,
                        telegramBotService
                );
            } else {
                volunteer = volunteersRepository.findByIdChat(callbackQuery.getFrom().getId());
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        INTERRUPT_CHAT_WITH_USER_MESSAGE,
                        telegramBotService
                );
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(volunteer.getAnimalOwner().getIdChat()),
                        INTERRUPT_CHAT_WITH_VOLUNTEER_MESSAGE,
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
            }
            interruptChat(animalOwner,volunteer);
        }
    }

    /**
     * Метод разъединяет чат пользователя и волонтера.
     *
     * @param animalOwner - объект пользователя.
     * @param volunteer - объект волонтера.
     */
    private void interruptChat(AnimalOwner animalOwner, Volunteer volunteer) {
        AnimalOwner volunteerOwner = animalOwnerRepository.findByIdChat(volunteer.getIdChat());
        AnimalOwner animalOwnerInterrupt = volunteer.getAnimalOwner();

        if (!animalOwner.getIsVolunteer()) {
            animalOwner.setHelpVolunteer(false);
            animalOwner.setVolunteer(null);
            animalOwner.setInChat(false);
            animalOwnerRepository.save(animalOwner);
        } else {
            animalOwnerInterrupt.setHelpVolunteer(false);
            animalOwnerInterrupt.setVolunteer(null);
            animalOwnerInterrupt.setInChat(false);
            animalOwnerRepository.save(animalOwnerInterrupt);
        }

        volunteerOwner.setInChat(false);

        volunteer.setIsBusy(false);
        volunteer.setAnimalOwner(null);

        animalOwnerRepository.save(volunteerOwner);
        volunteersRepository.save(volunteer);
    }
}

