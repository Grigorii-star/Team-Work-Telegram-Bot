package skypro.TeamWorkTelegramBot.buttons.stages.saves;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.exception.UploadFileException;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.service.fileService.FileService;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import java.io.IOException;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;

@Slf4j
@Component
public class SavePhoto implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final ReportsRepository reportsRepository;
    private final FileService fileService;

    String[] buttonsText = {MENU_BUTTON};
    String[] buttonsCallData = {MENU};

    public SavePhoto(SendMessageService sendMessageService,
                     AnimalOwnerRepository animalOwnerRepository,
                     ReportsRepository reportsRepository,
                     FileService fileService) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.reportsRepository = reportsRepository;
        this.fileService = fileService;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        try {
            uploadReport(update.getMessage(), telegramBotService);
        } catch (IOException | NullPointerException e) {
            log.error("Exception in method execute SaveReportAboutPet class");
        }
    }

    private void uploadReport(Message message, TelegramBotService telegramBotService) throws IOException {
        log.info("Invoked a method for uploading animal report");

        if (message.hasPhoto() && message.getPhoto() != null && message.getCaption() != null) {

            try {
                fileService.animalReport(message);
                AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(message.getChatId());
                animalOwner.setCanSendReport(false);
                animalOwnerRepository.save(animalOwner);

                sendMessageService.SendMessageToUser(
                        String.valueOf(message.getChatId()),
                        "Фото и отчет успешно загружены. Перейдите в главное меню.",
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );

            } catch (UploadFileException e) {
                log.error("UploadFileException");

                sendMessageService.SendMessageToUser(
                        String.valueOf(message.getChatId()),
                        "К сожалению загрузка фото и отчета не удалась.",
                        telegramBotService
                );
            }

        } else {
            sendMessageService.SendMessageToUser(
                    String.valueOf(message.getChatId()),
                    "Неверный формат фото и отчета.",
                    telegramBotService
            );
        }
    }
}
