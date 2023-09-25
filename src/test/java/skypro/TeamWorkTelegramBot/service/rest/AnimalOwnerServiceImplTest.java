package skypro.TeamWorkTelegramBot.service.rest;

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
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest
class AnimalOwnerServiceImplTest {
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

    @InjectMocks
    private ReportController reportController;
    @InjectMocks
    private AnimalOwnerController animalOwnerController;
    @InjectMocks
    private ShelterController shelterController;
    @InjectMocks
    private VolunteerController volunteerController;
    @Test
    void getAllVolunteers() {
        Volunteer volunteer = new Volunteer(1, 123L, "Alex", false);
        AnimalOwner animalOwner = new AnimalOwner(1, 123L, "+7", true,
                true, false, false, true, false,
                false, false, volunteer,
                Shelter.builder()
                        .id(1)
                        .name("Alex")
                        .build());

        List<AnimalOwner> animalOwners = new ArrayList<>();
        animalOwners.add(animalOwner);

        when(animalOwnerRepository.findByIsVolunteer()).thenReturn(animalOwners);
        when(animalOwnerRepository.save(animalOwner)).thenReturn(animalOwner);

        assertTrue(animalOwner.getIsVolunteer());
    }
}