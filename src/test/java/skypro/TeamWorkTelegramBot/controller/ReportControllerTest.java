//package skypro.TeamWorkTelegramBot.controller;
//
//import lombok.SneakyThrows;
//import org.json.JSONObject;
//
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.http.MediaType;
//
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import skypro.TeamWorkTelegramBot.entity.Report;
//import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
//import skypro.TeamWorkTelegramBot.service.restApiServices.ReportService;
//
//import java.util.ArrayList;
//import java.util.List;
//import static org.mockito.Mockito.when;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest
//class ReportControllerTest {
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReportService reportService;
//
//    @MockBean
//    private ReportsRepository reportsRepository;
//
//    @MockBean
//    private ShelterController shelterController;
//
//    @MockBean
//    private VolunteerController volunteerController;
//
//    @InjectMocks
//    private ReportController reportController;
//
//
//    @SneakyThrows
//    @Test
//    public void find() {
//
//        Report report = new Report(1, "TestName", "test/path", 10L, "testType");
//
//        when(reportService.findReport(1L)).thenReturn(report);
//
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/animal/1")
//                        .accept(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk());
//
////todo неясно почему не проходит
//
////        when(animalService.findAnimal(2L)).thenReturn(null);
////
////        mockMvc.perform(MockMvcRequestBuilders
////                        .get("/animal/2")
////                        .accept(MediaType.APPLICATION_JSON))
////                .andExpect(status().is(400));
//
//    }
//
//    @SneakyThrows
//    @Test
//    void getAll() {
//        Report report1 = new Report(1, "TestName1", "test/path1", 10L, "testType");
//        Report report2 = new Report(2, "TestName2", "test/path2", 10L, "testType");
//
//        List<Report> reportList = new ArrayList<>();
//        reportList.add(report1);
//        reportList.add(report2);
//
//
//        JSONObject animalObject = new JSONObject();
//        animalObject.put("id", "2");
//        animalObject.put("name","TestName");
//        animalObject.put("filePath","test/path");
//        animalObject.put("type","testType");
//
//
//        when(reportService.getAllReports()).thenReturn(reportList);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/animal")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//
//    @SneakyThrows
//    @Test
//    void add() {
//        Report report = new Report(2, "TestName", "test/path", 10L, "testType");
//
//        JSONObject animalObject = new JSONObject();
//        animalObject.put("id", "2");
//        animalObject.put("name","TestName");
//        animalObject.put("filePath","test/path");
//        animalObject.put("type","testType");
//
//
//        when(reportService.findReport(2L)).thenReturn(report);
//        when(reportService.addAnimal(report)).thenReturn(report);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/animal")
//                .content(animalObject.toString())
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @SneakyThrows
//    @Test
//    void edit() {
//        Report report = new Report(2, "TestName", "test/path", 10L, "testType");
//
//        JSONObject animalObject = new JSONObject();
//        animalObject.put("id", "2");
//        animalObject.put("name","TestName");
//        animalObject.put("filePath","test/path");
//        animalObject.put("type","testType");
//
//
//        when(reportService.findReport(2L)).thenReturn(report);
//        when(reportService.addAnimal(report)).thenReturn(report);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/animal")
//                        .content(animalObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @SneakyThrows
//    @Test
//    void remove() {
//        Report report = new Report(2, "TestName", "test/path", 10L, "testType");
//
//        JSONObject animalObject = new JSONObject();
//        animalObject.put("id", "2");
//        animalObject.put("name","TestName");
//        animalObject.put("filePath","test/path");
//        animalObject.put("type","testType");
//
//
//        when(reportService.findReport(2L)).thenReturn(report);
//        when(reportService.addAnimal(report)).thenReturn(report);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/animal/2")
//                        .content(animalObject.toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//
//}