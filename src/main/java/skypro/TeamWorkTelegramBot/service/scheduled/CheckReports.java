package skypro.TeamWorkTelegramBot.service.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import skypro.TeamWorkTelegramBot.entity.DateAndTimeReport;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.DateAndTimeReportRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.*;

/**
 * Класс для проверки даты отчетов пользователя о питомсе.
 */
@Slf4j
@Component
public class CheckReports {
    private final SendMessageService sendMessageService;
    private final VolunteersRepository volunteersRepository;
    private final TelegramBotService telegramBotService;
    private final DateAndTimeReportRepository dateAndTimeReportRepository;

    public CheckReports(SendMessageService sendMessageService,
                        VolunteersRepository volunteersRepository,
                        TelegramBotService telegramBotService,
                        DateAndTimeReportRepository dateAndTimeReportRepository) {
        this.sendMessageService = sendMessageService;
        this.volunteersRepository = volunteersRepository;
        this.telegramBotService = telegramBotService;
        this.dateAndTimeReportRepository = dateAndTimeReportRepository;
    }

    /**
     * Метод проверяет базу данных раз в сутки и отправляет сообщения
     * пользователю и волонтеру.
     */
    // @Scheduled(cron = "0 0 21 * * *")
    @Scheduled(cron = "0 * * * * *")
    public void checkReportsPet() {
        log.info("Invoked scheduled method checkReportsPet");
        List<DateAndTimeReport> reportList = dateAndTimeReportRepository.findAll();

        Volunteer volunteer = volunteersRepository.findDistinctFirstByIsBusy(false);

        for (DateAndTimeReport report : reportList) {
            Long chatIdOwner = report.getIdChatAnimalOwner();
            Long chatIdVolunteer1 = volunteer.getIdChat();

            LocalDateTime date = LocalDateTime.now();
            LocalDateTime dateActual = report.getDateActual();
            LocalDateTime dateFirst = report.getDateFirst();

            long difference1 = Duration.between(dateActual, date).toDays();
            System.out.println(difference1);
            long difference2 = Duration.between(dateFirst, date).toDays();

            if (difference1 >= 1 && difference1 <= 2) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdOwner),
                        SAVE_REPORT_ONE_DAY_ERROR_TO_USER_MESSAGE,
                        telegramBotService
                );
            }
            if (difference1 > 2) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdVolunteer1),
                        SAVE_REPORT_TWO_DAYS_ERROR_TO_VOLUNTEER_MESSAGE + chatIdOwner,
                        telegramBotService
                );

                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdOwner),
                        SAVE_REPORT_TWO_DAYS_ERROR_TO_USER_MESSAGE,
                        telegramBotService
                );
            }

            if (difference2 == 30) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdVolunteer1),
                        SAVE_REPORT_THIRTY_DAYS_SUCCESSFULLY_TO_VOLUNTEER_MESSAGE + chatIdOwner,
                        telegramBotService
                );
            }
            if (difference2 == 44) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdVolunteer1),
                        SAVE_REPORT_FOURTEEN_ADDITIONAL_DAYS_SUCCESSFULLY_TO_VOLUNTEER_MESSAGE + chatIdOwner,
                        telegramBotService
                );
            }
            if (difference2 == 60) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdVolunteer1),
                        SAVE_REPORT_THIRTY_ADDITIONAL_DAYS_SUCCESSFULLY_TO_VOLUNTEER_MESSAGE + chatIdOwner,
                        telegramBotService
                );
            }
        }
    }
}