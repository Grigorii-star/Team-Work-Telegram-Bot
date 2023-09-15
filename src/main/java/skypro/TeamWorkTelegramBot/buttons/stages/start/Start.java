package skypro.TeamWorkTelegramBot.buttons.stages.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс, приветсвтвующий пользователя и дают выбрать приют кошек или собак
 */
@Component
@Slf4j
public class Start implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    String[] buttonsText = {DOG_SHELTER_BUTTON,
                            CAT_SHELTER_BUTTON,
                            BECOME_A_VOLUNTEER_BUTTON};
    String[] buttonsCallData = {DOG,
                                CAT,
                                VOLUNTEER};

    public Start(SendMessageService sendMessageService,
                 AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Содержит id пользователя и сообщение для пользователя,
     * отправляет сообщение, полученное из текстового файла,
     * и необходимые кнопки для пользователя
     *
     * @param update - объект телеграмма для получения значений из телеграмм бота
     */

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long textChatId = 0L;
        String start = "";

        Long queryChatId = 0L;
        String data = "";
        try {
            textChatId = update.getMessage().getChatId();
            start = update.getMessage().getText();

            queryChatId = update.getCallbackQuery().getFrom().getId();
            data = update.getCallbackQuery().getData();

        } catch (NullPointerException e) {
            log.error("Error NullPointerException по update.getMessage().getChatId()");
        }

        AnimalOwner animalOwnerText = animalOwnerRepository.findByIdChat(textChatId);
        AnimalOwner animalOwnerQuery = animalOwnerRepository.findByIdChat(queryChatId);

        if (start.equals("/start") && animalOwnerText.getRegistered() == null) {
            animalOwnerText.setRegistered(true);
            animalOwnerText.setCanSaveContact(false);
            animalOwnerText.setBeVolunteer(false); //добавил
            animalOwnerText.setTookTheAnimal(false);
            animalOwnerText.setHelpVolunteer(false); //добавил
            animalOwnerRepository.save(animalOwnerText);
            sendMessageService.SendMessageToUser(String.valueOf(textChatId), GREETING_MESSAGE, telegramBotService);
            sendMessageService.SendMessageToUser(
                    String.valueOf(textChatId),
                    getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"),
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if ((start.equals("/start") && animalOwnerText.getRegistered())) {
            animalOwnerText.setCanSaveContact(false);
            //animalOwner.setBeVolunteer(false); //добавил, а может тут и не надо
            animalOwnerRepository.save(animalOwnerText);
            sendMessageService.SendMessageToUser(
                    String.valueOf(textChatId),
                    "Можете выбрать приют.",
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
        else if ((data.equals("меню_выбора_животного") && animalOwnerQuery.getRegistered()) && animalOwnerQuery.getBeVolunteer()) {
            animalOwnerQuery.setCanSaveContact(false);
            //animalOwner.setBeVolunteer(false); //добавил, а может тут и не надо
            animalOwnerRepository.save(animalOwnerQuery);
            sendMessageService.SendMessageToUser(
                    String.valueOf(queryChatId),
                    "Можете выбрать приют.",
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
    }
}
