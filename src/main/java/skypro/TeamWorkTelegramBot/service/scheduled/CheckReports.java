package skypro.TeamWorkTelegramBot.service.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import skypro.TeamWorkTelegramBot.entity.DateAndTimeReport;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.DateAndTimeReportRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CheckReports {
    private final ReportsRepository reportsRepository;
    private final SendMessageService sendMessageService;
    private final VolunteersRepository volunteersRepository;
    private final TelegramBotService telegramBotService;
    private final DateAndTimeReportRepository dateAndTimeReportRepository;

    public CheckReports(ReportsRepository reportsRepository,
                        SendMessageService sendMessageService,
                        VolunteersRepository volunteersRepository,
                        TelegramBotService telegramBotService,
                        DateAndTimeReportRepository dateAndTimeReportRepository) {
        this.reportsRepository = reportsRepository;
        this.sendMessageService = sendMessageService;
        this.volunteersRepository = volunteersRepository;
        this.telegramBotService = telegramBotService;
        this.dateAndTimeReportRepository = dateAndTimeReportRepository;
    }

    String[] buttonsText = {"Поздравить владельца",
            "Продлить испытательный срок на 14 дней",
            "Продлить испытательный срок на 30 дней",
            "Отказать во владении животного"};
    String[] buttonsCallData = {"Поздравить",
            "Продлить_14",
            "Продлить_30",
            "Отказать"};

    String[] buttonsText1 = {"Поздравить владельца",
            "Отказать во владении животного"};
    String[] buttonsCallData1 = {"Поздравить",
            "Отказать"};

    //@Scheduled(cron = "0 * * * * *")   // @Scheduled(cron = "0 0 21 * * *")
    public void checkReportsPet() {
        List<DateAndTimeReport> reportList = dateAndTimeReportRepository.findAll();

        //List<Report> reportList1 = reportsRepository.findAll();

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
                        "Отправьте отчёт",
                        telegramBotService
                );
            }
            if (difference1 > 2) {
                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdVolunteer1),
                        "Владелец животного не присылает отчёт два дня! Для связи с владельцем" +
                                " напиши его chatId в формате: --" + chatIdOwner,
                        telegramBotService
                );

                sendMessageService.SendMessageToUser(
                        String.valueOf(chatIdOwner),
                        "Ты не присылал отчёт более двух дней! С тобой свяжется волонтёр",
                        telegramBotService
                );
            }

            if (difference2 == 30) {
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(chatIdVolunteer1),
                        "Владелец животного 30 дней присылает отчёт! Для связи с владельцем" +
                                " напиши его chatId в формате: --" + chatIdOwner
                                + " Или выбери что сделать далее:",
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
            }
            if (difference2 == 44) {
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(chatIdVolunteer1),
                        "У владелеца животного прошло 14 дней испытательного срока! Для связи с владельцем" +
                                " напиши его chatId в формате: --" + chatIdOwner
                                + " Или выбери что сделать далее:",
                        buttonsText1,
                        buttonsCallData1,
                        telegramBotService
                );
            }
            if (difference2 == 60) {
                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(chatIdVolunteer1),
                        "У владелеца животного прошло 30 дней испытательного срока! Для связи с владельцем" +
                                " напиши его chatId в формате: --" + chatIdOwner
                                + " Или выбери что сделать далее:",
                        buttonsText1,
                        buttonsCallData1,
                        telegramBotService
                );
            }
        }
    }
}