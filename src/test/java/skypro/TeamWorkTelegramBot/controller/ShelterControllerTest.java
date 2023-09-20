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
import skypro.TeamWorkTelegramBot.entity.Shelter;
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
class ShelterControllerTest {

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
    private ShelterController shelterController;

    @SneakyThrows
    @Test
    void add() {
        Shelter shelter = new Shelter(2,"testShelter");

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("id", "2");
        shelterObject.put("name","TestName");


        when(shelterService.findShelter(2)).thenReturn(shelter);
        when(shelterService.addShelter(shelter)).thenReturn(shelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/animal-shelter")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void find() {
        Shelter shelter = new Shelter(1,"testShelter");
        when(shelterService.findShelter(1)).thenReturn(shelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animal-shelter/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getAll() {
        Shelter shelter = new Shelter(1,"testShelter");
        Shelter shelter2 = new Shelter(2,"testShelter2");

        List<Shelter> shelterList = new ArrayList<>();
        shelterList.add(shelter);
        shelterList.add(shelter2);

        when(shelterService.getAllShelters()).thenReturn(shelterList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animal-shelter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @SneakyThrows
    @Test
    void edit() {
        Shelter shelter = new Shelter(1,"TestName");

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("id", "1");
        shelterObject.put("name","TestName");


        when(shelterService.editShelter(shelter)).thenReturn(shelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/animal-shelter")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void remove() {
        Shelter shelter = new Shelter(1,"TestName");

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("id", "1");
        shelterObject.put("name","TestName");


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animal-shelter/1")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}