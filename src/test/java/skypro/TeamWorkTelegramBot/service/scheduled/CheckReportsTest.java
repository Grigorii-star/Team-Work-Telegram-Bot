package skypro.TeamWorkTelegramBot.service.scheduled;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import skypro.TeamWorkTelegramBot.controller.AnimalOwnerController;
import skypro.TeamWorkTelegramBot.controller.ReportController;
import skypro.TeamWorkTelegramBot.controller.ShelterController;
import skypro.TeamWorkTelegramBot.controller.VolunteerController;
import skypro.TeamWorkTelegramBot.entity.DateAndTimeReport;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.*;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.rest.AnimalOwnerService;
import skypro.TeamWorkTelegramBot.service.rest.ReportService;
import skypro.TeamWorkTelegramBot.service.rest.ShelterService;
import skypro.TeamWorkTelegramBot.service.rest.VolunteerService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.SAVE_REPORT_ONE_DAY_ERROR_TO_USER_MESSAGE;

@WebMvcTest
class CheckReportsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;
    @MockBean
    private AnimalOwnerService animalOwnerService;
    @MockBean
    private ShelterService shelterService;
    @MockBean
    private VolunteerService volunteerService;
    @MockBean
    private ReportsRepository reportsRepository;
    @MockBean
    private AnimalOwnerRepository animalOwnerRepository;
    @MockBean
    private SheltersRepository sheltersRepository;
    @MockBean
    private VolunteersRepository volunteersRepository;
    @MockBean
    DateAndTimeReportRepository dateAndTimeReportRepository;

    @InjectMocks
    private ReportController reportController;
    @InjectMocks
    private AnimalOwnerController animalOwnerController;
    @InjectMocks
    private ShelterController shelterController;
    @InjectMocks
    private VolunteerController volunteerController;

    @InjectMocks
    private TelegramBotService telegramBotService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void checkReportsPet() {
        Integer id = 1;
        Long idChatAnimalOwner = 1L;
        LocalDate dateFirst = LocalDate.of(2023, 9, 20);
        LocalTime timeFirst = LocalTime.of(9, 00, 00);
        LocalDateTime dateAndTimeFirst = LocalDateTime.of((dateFirst), (timeFirst));

        LocalDate dateActual = LocalDate.of(2023, 9, 23);
        LocalTime timeActual = LocalTime.of(9, 00, 00);
        LocalDateTime dateAndTimeActual = LocalDateTime.of(dateActual, timeActual);

        DateAndTimeReport dateAndTimeReport = new DateAndTimeReport(id, dateAndTimeActual,
                dateAndTimeFirst, idChatAnimalOwner);

        long difference1 = Duration.between(dateAndTimeActual, dateAndTimeActual).toDays();

        long difference2 = Duration.between(dateAndTimeFirst, dateAndTimeActual).toDays();
        assertEquals(difference1, 0);
        assertEquals(difference2, 3);
        assertEquals(dateAndTimeReport, new DateAndTimeReport(id, dateAndTimeActual,
                dateAndTimeFirst, idChatAnimalOwner));
    }
}