package skypro.TeamWorkTelegramBot.buttons.stages.mainMenu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.GREETING_MESSAGE;

/**
 * Класс, который нужен для формирования главного меню приюта
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
     * данный метод обрабатывает разные варианты callData, устанавливает настройки "любителя собак" или
     * "любителя кошек" в зависимости от выбранной опции, сохраняет изменения в репозитории и отправляет
     * приветственное сообщение с кнопками пользователю.
     * @param callbackQuery объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
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
                    GREETING_MESSAGE,
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
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if (callbackQuery.getData().equals(MENU)) {
            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(callbackQuery.getFrom().getId()),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if (callbackQuery.getData().equals(CHAT)) {

            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());

            //если пользователь
            Volunteer volunteer;
            if (!animalOwner.getBeVolunteer()) {
                volunteer = volunteersRepository.findByAnimalOwner(animalOwner);
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        GREETING_MESSAGE,
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
                sendMessageService.SendMessageToUser(
                        String.valueOf(volunteer.getIdChat()),//поменять чат на волонтера
                        "Связь с пользователем прервана",
                        telegramBotService
                );
            } else {
                volunteer = volunteersRepository.findByIdChat(callbackQuery.getFrom().getId());
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        "Связь с пользователем прервана",
                        telegramBotService
                );
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(volunteer.getAnimalOwner().getIdChat()),
                        "Связь с волонтером прервана",
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
            }
            interruptChat(animalOwner,volunteer);
        }
    }

    private void interruptChat(AnimalOwner animalOwner, Volunteer volunteer) {
        animalOwner.setHelpVolunteer(false);
        animalOwner.setVolunteer(null);
        volunteer.setIsBusy(false);
        volunteer.setAnimalOwner(null);
        //сохраняем в базу данных все
        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);
    }
}

