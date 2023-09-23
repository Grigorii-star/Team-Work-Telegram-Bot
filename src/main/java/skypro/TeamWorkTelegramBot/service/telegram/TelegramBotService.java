package skypro.TeamWorkTelegramBot.service.telegram;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.buttons.stages.get.CatAndDogGetAnimalFromTheShelter;
import skypro.TeamWorkTelegramBot.buttons.stages.saves.SaveContacts;
import skypro.TeamWorkTelegramBot.buttons.stages.saves.SavePhoto;
import skypro.TeamWorkTelegramBot.buttons.stages.volunteer.BecomeVolunteer;
import skypro.TeamWorkTelegramBot.buttons.stages.volunteer.ConnectionVolunteerOwner;
import skypro.TeamWorkTelegramBot.buttons.stages.volunteer.HelpVolunteer;
import skypro.TeamWorkTelegramBot.configuration.TelegramBotConfiguration;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.buttons.stages.get.GetAnimalFromTheShelter;
import skypro.TeamWorkTelegramBot.buttons.stages.information.CatAndDogInformation;
import skypro.TeamWorkTelegramBot.buttons.stages.information.Information;
import skypro.TeamWorkTelegramBot.buttons.stages.menu.MainMenu;
import skypro.TeamWorkTelegramBot.buttons.stages.saves.CanSaveReportAboutPet;
import skypro.TeamWorkTelegramBot.buttons.stages.saves.CanSaveContacts;
import skypro.TeamWorkTelegramBot.buttons.stages.start.Start;
import skypro.TeamWorkTelegramBot.buttons.stages.volunteer.CallVolunteer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCommands.*;

/**
 * Класс принимает все сообщения из Telegram бота и с помощью HashMap
 * перенаправляет их в необходимый класс для последующей обработки.
 */
@Slf4j
@Getter
@Service
public class TelegramBotService extends TelegramLongPollingBot {
    private final TelegramBotConfiguration telegramBotConfiguration;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final Start start;
    private final MainMenu mainMenu;
    private final Information information;
    private final CatAndDogInformation catAndDogInformation;
    private final CanSaveContacts canSaveContacts;
    private final CallVolunteer callVolunteer;
    private final CanSaveReportAboutPet canSaveReportAboutPet;
    private final GetAnimalFromTheShelter getAnimalFromTheShelter;
    private final CatAndDogGetAnimalFromTheShelter catAndDogGetAnimalFromTheShelter;
    private final HelpVolunteer helpVolunteer;
    private final BecomeVolunteer becomeVolunteer;
    private final SavePhoto savePhoto;
    private final SaveContacts saveContacts;
    private final ConnectionVolunteerOwner connectionVolunteerOwner;

    /**
     * HashMap которая хранит бины классов наследников абстракного класса CommandAbstractClass.
     *
     * @see CommandAbstractClass
     */
    private final Map<String, CommandAbstractClass> commandMap;

    public TelegramBotService(TelegramBotConfiguration telegramBotConfiguration,
                              AnimalOwnerRepository animalOwnerRepository,
                              Start start, MainMenu mainMenu, Information information,
                              CatAndDogInformation catAndDogInformation, CanSaveContacts canSaveContacts,
                              CallVolunteer callVolunteer, CanSaveReportAboutPet canSaveReportAboutPet,
                              GetAnimalFromTheShelter getAnimalFromTheShelter,
                              CatAndDogGetAnimalFromTheShelter catAndDogGetAnimalFromTheShelter,
                              HelpVolunteer helpVolunteer, BecomeVolunteer becomeVolunteer,
                              SavePhoto savePhoto, SaveContacts saveContacts,
                              ConnectionVolunteerOwner connectionVolunteerOwner) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        this.animalOwnerRepository = animalOwnerRepository;
        this.start = start;
        this.mainMenu = mainMenu;
        this.information = information;
        this.catAndDogInformation = catAndDogInformation;
        this.canSaveContacts = canSaveContacts;
        this.callVolunteer = callVolunteer;
        this.canSaveReportAboutPet = canSaveReportAboutPet;
        this.getAnimalFromTheShelter = getAnimalFromTheShelter;
        this.catAndDogGetAnimalFromTheShelter = catAndDogGetAnimalFromTheShelter;
        this.helpVolunteer = helpVolunteer;
        this.becomeVolunteer = becomeVolunteer;
        this.savePhoto = savePhoto;
        this.saveContacts = saveContacts;
        this.connectionVolunteerOwner = connectionVolunteerOwner;

        this.commandMap = new HashMap<>();
        this.init();
    }

    /**
     * Метод для добавления классов в HashMap commandMap.
     */
    private void init() {
        commandMap.put(START_COMMAND, start);
        commandMap.put(MAIN_MENU_COMMAND, mainMenu);
        commandMap.put(INFORMATION_COMMAND, information);
        commandMap.put(CAT_AND_DOG_INFO_COMMAND, catAndDogInformation);
        commandMap.put(GET_ANIMAL_COMMAND, getAnimalFromTheShelter);
        commandMap.put(CAT_AND_DOG_GET_ANIMAL_COMMAND, catAndDogGetAnimalFromTheShelter);
        commandMap.put(BECOME_VOLUNTEER_COMMAND, becomeVolunteer);
        commandMap.put(CALL_VOLUNTEER_COMMAND, callVolunteer);
        commandMap.put(HELP_VOLUNTEER_COMMAND, helpVolunteer);
        commandMap.put(CAN_SAVE_CONTACTS_COMMAND, canSaveContacts);
        commandMap.put(SAVE_REPORT_COMMAND, canSaveReportAboutPet);
        commandMap.put(SAVE_PHOTO_COMMAND, savePhoto);
        commandMap.put(SAVE_CONTACTS_COMMAND, saveContacts);
        commandMap.put("connectionVolunteerOwner", connectionVolunteerOwner);
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfiguration.getToken();
    }

    /**
     * Основной метод, который получает из объекта Update Telegram бота значения и делегирует эти
     * сообщения классам наследников абстракного класса CommandAbstractClass, которые содержаться
     * в HashMap commandMap.<br>
     * Метод создает нового пользователя в БД по chatId Telegram бота.
     *
     * @param update - объект телеграм для получения значений из Telegram бота.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(update.getMessage().getChatId());
            if (checkAnimalOwner == null) {
                AnimalOwner animalOwner = new AnimalOwner();
                animalOwner.setIdChat(update.getMessage().getChatId());
                animalOwnerRepository.save(animalOwner);
            }

            String patternNumber = "([\\+]?[7|8][\\s-(]?[9][0-9]{2}[\\s-)]?)?([\\d]{3})[\\s-]?([\\d]{2})[\\s-]?([\\d]{2})";
            boolean matchesResult = Pattern.matches(patternNumber, update.getMessage().getText());

            if (!update.getMessage().getText().isEmpty() && update.getMessage().getText().equals(START_TELEGRAM_BOT_COMMAND)) {
                commandMap.get(START_COMMAND).messagesExtractor(update.getMessage(), this);
            }
            if (!update.getMessage().getText().isEmpty() && matchesResult) {
                commandMap.get(SAVE_CONTACTS_COMMAND).messagesExtractor(update.getMessage(), this);
            }
            if (!update.getMessage().getText().isEmpty() && !update.getMessage().getText().equals(START_TELEGRAM_BOT_COMMAND)
                    && checkAnimalOwner != null && !matchesResult) {

                if (checkAnimalOwner.getInChat()) {
                    commandMap.get(HELP_VOLUNTEER_COMMAND).messagesExtractor(update.getMessage(), this);
                }
                if (checkAnimalOwner.getIsVolunteer() || update.getMessage().getText().contains("-")) { //добавил || update.getMessage().getText().contains("--")
                    commandMap.get("connectionVolunteerOwner").messagesExtractor(update.getMessage(), this);
                }
            }
            if (checkAnimalOwner != null && checkAnimalOwner.getCanSendReport() && update.getMessage().hasText()
                    && !update.getMessage().getText().equals(START_TELEGRAM_BOT_COMMAND)) {
                commandMap.get(SAVE_PHOTO_COMMAND).messagesExtractor(update.getMessage(), this);
            }
        }

        else if (update.hasCallbackQuery()) {
            String commandTextFromButtons = getCommandTextFromButtons(update.getCallbackQuery());
            commandMap.get(commandTextFromButtons).callBackQueryExtractor(update.getCallbackQuery(), this);
        }

        else if (update.getMessage().hasPhoto()) {
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(update.getMessage().getChatId());
            if (animalOwner.getCanSendReport() && update.getMessage().hasPhoto() && update.getMessage().getPhoto() != null) {
                commandMap.get(SAVE_PHOTO_COMMAND).messagesExtractor(update.getMessage(), this);
            }
        }
    }

    /**
     * Метод принимает callbackQuery значения, достает из них Data текст команды от кнопок,
     * которые были нажаты пользователем в Telegram боте и перебирает их в switch.
     * При совпадении возвращает ключ значение для HashMap.
     *
     * @param callbackQuery - объект Telegram для получения значений из Telegram бота.
     * @return String ключ-значение для HashMap commandMap.
     */
    private static String getCommandTextFromButtons(CallbackQuery callbackQuery) {
        String commandTextFromButtons = callbackQuery.getData();

        switch (commandTextFromButtons) {
            case DOG:
            case CAT:
            case MENU:
            case CHAT:
                commandTextFromButtons = MAIN_MENU_COMMAND;
                break;
            case INFO:
                commandTextFromButtons = INFORMATION_COMMAND;
                break;
            case ABOUT_SHELTER:
            case SCHEDULE:
            case SECURITY:
            case SAFETY_PRECAUTIONS:
                commandTextFromButtons = CAT_AND_DOG_INFO_COMMAND;
                break;
            case POST_CONTACT:
                commandTextFromButtons = CAN_SAVE_CONTACTS_COMMAND;
                break;
            case BEST_VOLUNTEER:
                commandTextFromButtons = BECOME_VOLUNTEER_COMMAND;
                break;
            case REPORT:
                commandTextFromButtons = SAVE_REPORT_COMMAND;
                break;
            case GET_AN_ANIMAL:
                commandTextFromButtons = GET_ANIMAL_COMMAND;
                break;
            case MEETING_DOG_RULES:
            case MEETING_CAT_RULES:
            case DOC_LIST:
            case TRANSPORTATION:
            case PUPPY_HOUSE:
            case PUSSY_HOUSE:
            case PET_HOUSE:
            case INVALID_HOUSE:
            case DOG_HANDLER_ADVICE:
            case DOG_HANDLER_CONTACTS:
            case REFUSAL_REASONS:
                commandTextFromButtons = CAT_AND_DOG_GET_ANIMAL_COMMAND;
                break;
            case CALL_VOLUNTEER:
                commandTextFromButtons = CALL_VOLUNTEER_COMMAND;
        }
        return commandTextFromButtons;
    }
}