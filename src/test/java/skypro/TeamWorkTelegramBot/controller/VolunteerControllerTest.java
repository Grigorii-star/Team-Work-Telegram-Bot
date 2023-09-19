package skypro.TeamWorkTelegramBot.controller;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;
import skypro.TeamWorkTelegramBot.service.rest.ReportService;
import skypro.TeamWorkTelegramBot.service.rest.ShelterService;
import skypro.TeamWorkTelegramBot.service.rest.VolunteerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class VolunteerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ShelterService shelterService;

    @MockBean
    SheltersRepository sheltersRepository;

    @MockBean
    ReportService reportService;

    @MockBean
    VolunteerService volunteerService;

    @MockBean
    AnimalOwnerRepository animalOwnerRepository;

    @InjectMocks
    VolunteerController volunteerController;


    @SneakyThrows
    @Test
    void add() {
        Volunteer volunteer = new Volunteer(1,1L,"testName",false);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", "1");
        volunteerObject.put("idChat", "1");
        volunteerObject.put("name","TestName");
        volunteerObject.put("isBusy","false");


        when(volunteerService.findVolunteer(1)).thenReturn(volunteer);
        when(volunteerService.addVolunteer(volunteer)).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getAll() {
        Volunteer volunteer = new Volunteer(1,1L,"testName",false);
        Volunteer volunteer2 = new Volunteer(2,2L,"testName2",false);


        List<Volunteer> volunteerList = new ArrayList<>();
        volunteerList.add(volunteer);
        volunteerList.add(volunteer2);

        when(volunteerService.getAllVolunteers()).thenReturn(volunteerList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void find() {
        Volunteer volunteer = new Volunteer(1,1L,"testName",false);

        when(volunteerService.findVolunteer(1)).thenReturn(volunteer);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void edit() {
        Volunteer volunteer = new Volunteer(1,1L,"testName",false);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", "1");
        volunteerObject.put("idChat", "1");
        volunteerObject.put("name","TestName");
        volunteerObject.put("isBusy","false");


        when(volunteerService.editVolunteer(volunteer)).thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/volunteer")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @SneakyThrows
    @Test
    void remove() {
        Volunteer volunteer = new Volunteer(1,1L,"testName",false);

        JSONObject volunteerObject = new JSONObject();
        volunteerObject.put("id", "1");
        volunteerObject.put("idChat", "1");
        volunteerObject.put("name","TestName");
        volunteerObject.put("isBusy","false");



        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/1")
                        .content(volunteerObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}