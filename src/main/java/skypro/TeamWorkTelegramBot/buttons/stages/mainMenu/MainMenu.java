package skypro.TeamWorkTelegramBot.buttons.stages.mainMenu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
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
public class MainMenu implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;


    String[] buttonsText = {GET_INFO_SHELTER_BUTTON,
                            GET_PET_BUTTON,
                            REPORT_BUTTON,
                            CALL_VOLUNTEER_BUTTON};
    String[] buttonsCallData = {INFO,
                                GET_AN_ANIMAL,
                                REPORT,
                                CALL_VOLUNTEER};

    public MainMenu(SendMessageService sendMessageService,
                    AnimalOwnerRepository animalOwnerRepository, VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
    }

    /**
     * данный метод обрабатывает разные варианты callData, устанавливает настройки "любителя собак" или
     * "любителя кошек" в зависимости от выбранной опции, сохраняет изменения в репозитории и отправляет
     * приветственное сообщение с кнопками пользователю.
     * @param update объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();
        String callData = update.getCallbackQuery().getData();

        if (callData.equals(DOG)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
            animalOwner.setDogLover(true);
            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if (callData.equals(CAT)) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
            animalOwner.setDogLover(false);
            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if (callData.equals(MENU)) {
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if (callData.equals("чат")) {

            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
            Volunteer volunteer = new Volunteer();
            try {
                volunteer = volunteersRepository.findByAnimalOwner(animalOwner);
            } catch (NullPointerException e) {
                log.error("NullPointerException");
            }


            //если пользователь
            if (!animalOwner.getBeVolunteer()) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
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

                sendMessageService.SendMessageToUser(
                        String.valueOf(chatId),
                        "Связь с пользователем прервана",
                        telegramBotService
                );
                sendMessageService.SendMessageToUser(
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

