package skypro.TeamWorkTelegramBot.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.stages.GetAnimal.CatAndDogGetAnimalFromTheShelter;
import skypro.TeamWorkTelegramBot.configuration.TelegramBotConfiguration;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.buttons.stages.GetAnimal.GetAnimalFromTheShelter;
import skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal.CatAndDogInformation;
import skypro.TeamWorkTelegramBot.buttons.stages.informationAboutTheAnimal.Information;
import skypro.TeamWorkTelegramBot.buttons.stages.mainMenu.MainMenu;
import skypro.TeamWorkTelegramBot.buttons.stages.saves.SaveReportAboutPet;
import skypro.TeamWorkTelegramBot.buttons.stages.saves.SaveUserContacts;
import skypro.TeamWorkTelegramBot.buttons.stages.start.Start;
import skypro.TeamWorkTelegramBot.buttons.stages.volunteer.CallVolunteer;


import java.util.HashMap;
import java.util.Map;

/**
 * Сервисный класс телеграмбота
 */
@Service
@Getter
public class TelegramBotService extends TelegramLongPollingBot {
    private final TelegramBotConfiguration telegramBotConfiguration;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final Start start;
    private final MainMenu mainMenu;
    private final Information information;
    private final CatAndDogInformation catAndDogInformation;
    private final SaveUserContacts saveUserContacts;
    private final CallVolunteer callVolunteer;
    private final SaveReportAboutPet saveReportAboutPet;
    private final GetAnimalFromTheShelter getAnimalFromTheShelter;
    private final CatAndDogGetAnimalFromTheShelter catAndDogGetAnimalFromTheShelter;

    private final Map<String, Command> commandMap;

    public TelegramBotService(TelegramBotConfiguration telegramBotConfiguration,
                              AnimalOwnerRepository animalOwnerRepository,
                              Start start, MainMenu mainMenu, Information information,
                              CatAndDogInformation catAndDogInformation, SaveUserContacts saveUserContacts,
                              CallVolunteer callVolunteer, SaveReportAboutPet saveReportAboutPet,
                              GetAnimalFromTheShelter getAnimalFromTheShelter,
                              CatAndDogGetAnimalFromTheShelter catAndDogGetAnimalFromTheShelter) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        this.animalOwnerRepository = animalOwnerRepository;
        this.start = start;
        this.mainMenu = mainMenu;
        this.information = information;
        this.catAndDogInformation = catAndDogInformation;
        this.saveUserContacts = saveUserContacts;
        this.callVolunteer = callVolunteer;
        this.saveReportAboutPet = saveReportAboutPet;
        this.getAnimalFromTheShelter = getAnimalFromTheShelter;
        this.catAndDogGetAnimalFromTheShelter = catAndDogGetAnimalFromTheShelter;

        this.commandMap = new HashMap<>();
        this.init();
    }

    private void init() {
        commandMap.put("start", start);
        commandMap.put("mainMenu", mainMenu);
        commandMap.put("information", information);
        commandMap.put("catAndDogInformation", catAndDogInformation);
        commandMap.put("saveUserContacts", saveUserContacts);
        commandMap.put("volunteer", callVolunteer);
        commandMap.put("saveReportAboutPet", saveReportAboutPet);
        commandMap.put("getAnimalFromTheShelter", getAnimalFromTheShelter);
        commandMap.put("catAndDogGetAnimalFromTheShelter", catAndDogGetAnimalFromTheShelter);
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
     * Основной метод, который перенаправляет в разные классы update,
     * в зависимости от его значений
     *
     * @param update - содержит message, callbackQuery
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();

            AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);
            if (checkAnimalOwner == null) {
                AnimalOwner animalOwner = new AnimalOwner();
                animalOwner.setIdChat(chatId);
                animalOwnerRepository.save(animalOwner);
            }

            if (!update.getMessage().getText().isEmpty() && update.getMessage().getText().equals("/start")) {
                String commandText = "start";
                commandMap.get(commandText).execute(update, this);
            }
        } else if (update.hasCallbackQuery()) {
            String commandTextFromButtons = getCommandTextFromButtons(update);

            commandMap.get(commandTextFromButtons).execute(update, this);
        }
    }

    private static String getCommandTextFromButtons(Update update) {
        String commandTextFromButtons = update.getCallbackQuery().getData();

        switch (commandTextFromButtons) {
            case "собака":
            case "кошка":
            case "меню":
                commandTextFromButtons = "mainMenu";
                break;
            case "инфо":
                commandTextFromButtons = "information";
                break;
            case "о_приюте":
            case "расписание":
            case "охрана":
            case "техника_безопасности":
                commandTextFromButtons = "catAndDogInformation";
                break;
            case "контакт":
                commandTextFromButtons = "saveUserContacts";
                break;
            case "волонтер":
                commandTextFromButtons = "volunteer";
                break;
            case "отчет":
                commandTextFromButtons = "saveReportAboutPet";
                break;
            case "взять_животное":
                commandTextFromButtons = "getAnimalFromTheShelter";
                break;
            case "правила_знакомства_собака":
            case "правила_знакомства_кошка":
            case "список_документов":
            case "транспортировка":
            case "дом_для_щенка":
            case "дом_для_котенка":
            case "дом_для_животного":
            case "дом_для_инвалида":
            case "советы_кинолога":
            case "контакты_кинолога":
            case "причина_отказа":
                commandTextFromButtons = "catAndDogGetAnimalFromTheShelter";
                break;
        }
        return commandTextFromButtons;
    }
}