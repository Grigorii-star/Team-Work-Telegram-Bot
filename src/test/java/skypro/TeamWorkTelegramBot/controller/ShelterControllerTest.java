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
import skypro.TeamWorkTelegramBot.entity.Animal;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalsRepository;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;
import skypro.TeamWorkTelegramBot.service.AnimalService;
import skypro.TeamWorkTelegramBot.service.ShelterService;
import skypro.TeamWorkTelegramBot.service.VolunteerService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    AnimalService animalService;

    @MockBean
    VolunteerService volunteerService;

    @InjectMocks
    private ShelterController shelterController;

    @SneakyThrows
    @Test
    void add() {
        Shelter shelter = new Shelter(2,"testShelter");

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("id", "2");
        shelterObject.put("name","TestName");


        when(shelterService.findShelter(2L)).thenReturn(shelter);
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
        when(shelterService.findShelter(1L)).thenReturn(shelter);

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
        Shelter shelter = new Shelter(1,"testShelter");

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("id", "2");
        shelterObject.put("name","TestName");


        when(shelterService.findShelter(2L)).thenReturn(shelter);
        when(shelterService.addShelter(shelter)).thenReturn(shelter);

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
        Shelter shelter = new Shelter(1,"testShelter");

        JSONObject shelterObject = new JSONObject();
        shelterObject.put("id", "2");
        shelterObject.put("name","TestName");


        when(shelterService.findShelter(2L)).thenReturn(shelter);
        when(shelterService.addShelter(shelter)).thenReturn(shelter);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animal-shelter/1")
                        .content(shelterObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}