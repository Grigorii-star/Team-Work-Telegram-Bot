package skypro.TeamWorkTelegramBot.controller;

import lombok.SneakyThrows;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.service.rest.ReportService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ReportControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @MockBean
    private ReportsRepository reportsRepository;

    @MockBean
    private ShelterController shelterController;

    @MockBean
    private VolunteerController volunteerController;

    @InjectMocks
    private ReportController reportController;

    @SneakyThrows
    @Test
    public void find() {

        LocalDateTime date = LocalDateTime.now();

        Report report1 = Report.builder()
                .id(1)
                .date(date)
                .report("testReport")
                .telegramFieldId("testFileId")
                .fileSize(10)
                .build();

        when(reportService.getPhoto(1)).thenReturn(report1);


        mockMvc.perform(MockMvcRequestBuilders
                .get("/animal-report/get-report?reportId=1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

//todo неясно почему не проходит

//        when(animalService.findAnimal(2L)).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/animal/2")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(400));

    }

    @SneakyThrows
    @Test
    void getReportsByPages() {

        LocalDateTime date = LocalDateTime.now();

        Report report1 = Report.builder()
                .id(1)
                .date(date)
                .report("testReport")
                .telegramFieldId("testFileId")
                .fileSize(10)
                .build();

        Report report2 = Report.builder()
                .id(2)
                .date(date)
                .report("testReport")
                .telegramFieldId("testFileId")
                .fileSize(10)
                .build();

        List<Report> reportList = new ArrayList<>();
        reportList.add(report1);
        reportList.add(report2);


//        JSONObject reportObject = new JSONObject();
//        reportObject.put("id", "2");
//        reportObject.put("name","TestName");
//        reportObject.put("filePath","test/path");
//        reportObject.put("type","testType");


        when(reportService.getAllReportsByPages(1,2)).thenReturn(reportList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animal-report/all-reports-by-pages")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    //todo не разобрался как делать экземпляры дтошки для репортов
//    @SneakyThrows
//    @Test
//    void getReportsByUserId() {
//
//        LocalDate date = LocalDate.now();
//
//        Shelter shelter1 = new Shelter(1,"testShelter");
//        AnimalOwner animalOwner1 = AnimalOwner.builder()
//                .id(1)
//                .idChat(1L)
//                .contactInformation("testInfo")
//                .registered(true)
//                .dogLover(true)
//                .tookTheAnimal(true)
//                .canSaveContact(true)
//                .beVolunteer(true)
//                .helpVolunteer(true)
//                .canSendReport(true)
//                .build();
//
//        ReportDTO report1 = new Report(1, date,"testReport", "testFileId", 10);
//
//        ReportDTO report1 = Report.builder()
//                .date(date)
//                .report("testReport")
//                .shelter(shelter1)
//                .animalOwner(animalOwner1)
//                .build();
//
//        ReportDTO report2 = Report.builder()
//                .id(2)
//                .date(date)
//                .report("testReport")
//                .shelter()
//                .animalOwner()
//                .build();
//
//        List<ReportDTO> reportList = new ArrayList<>();
//        reportList.add(report1);
//        reportList.add(report2);
//
//
////        JSONObject reportObject = new JSONObject();
////        reportObject.put("id", "2");
////        reportObject.put("name","TestName");
////        reportObject.put("filePath","test/path");
////        reportObject.put("type","testType");
//
//
//        when(reportService.getAllReportsByUserId(1)).thenReturn(reportList);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/animal-report/all-reports-by-userId?userId=1")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }


}