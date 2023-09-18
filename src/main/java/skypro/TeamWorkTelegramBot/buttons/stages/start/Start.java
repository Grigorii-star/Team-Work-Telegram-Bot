package skypro.TeamWorkTelegramBot.buttons.stages.start;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCommands.START_TELEGRAM_BOT_COMMAND;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс, приветсвтвующий пользователя и дают выбрать приют кошек или собак
 */
@Slf4j
@Component
public class Start extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    String[] buttonsText = {DOG_SHELTER_BUTTON,
                            CAT_SHELTER_BUTTON,
                            BECOME_A_VOLUNTEER_BUTTON};
    String[] buttonsCallData = {DOG,
                                CAT,
                                BEST_VOLUNTEER};

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
     * @param message - объект телеграмма для получения значений из телеграмм бота
     */

    @Override
    public void messagesExtractor(Message message, TelegramBotService telegramBotService) {
        Long textChatId = 0L;
        String start = "";

        try {
            textChatId = message.getChatId();
            start = message.getText();

        } catch (NullPointerException e) {
            log.error("Error NullPointerException по update.getMessage().getChatId()");
        }

        AnimalOwner animalOwnerText = animalOwnerRepository.findByIdChat(textChatId);

        if (start.equals(START_TELEGRAM_BOT_COMMAND) && animalOwnerText.getRegistered() == null) {
            animalOwnerText.setRegistered(true); // для повторного нажатия страт не выводилось приветствие
            animalOwnerText.setCanSaveContact(false); // чтобы можно было сохранить контактные данные только пройдя по кнопке
            animalOwnerText.setBeVolunteer(false); //для того, чтобы стать волонтером и пройти по кнопке
            animalOwnerText.setTookTheAnimal(false); //для того, чтобы взять животное и пройти по кнопке
            animalOwnerText.setHelpVolunteer(false); //для того, чтобы получить помощь и пройти по кнопке
            animalOwnerText.setCanSendReport(false); // для того, чтобы отправить отчет только нажав кнопку
            animalOwnerRepository.save(animalOwnerText);
            sendMessageService.SendMessageToUser(String.valueOf(textChatId), GREETING_MESSAGE, telegramBotService);
            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(textChatId),
                    getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"),
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        } else if ((start.equals(START_TELEGRAM_BOT_COMMAND) && animalOwnerText.getRegistered())) {
            animalOwnerText.setCanSaveContact(false);
            animalOwnerText.setCanSendReport(false);
            animalOwnerRepository.save(animalOwnerText);

            sendMessageService.SendMessageToUserWithButtons(
                    String.valueOf(textChatId),
                    "Можете выбрать приют.",
                    buttonsText,
                    buttonsCallData,
                    telegramBotService
            );
        }
    }
}
