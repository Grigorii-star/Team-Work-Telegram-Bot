package skypro.TeamWorkTelegramBot;

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
import skypro.TeamWorkTelegramBot.controller.AnimalOwnerController;
import skypro.TeamWorkTelegramBot.controller.ReportController;
import skypro.TeamWorkTelegramBot.controller.ShelterController;
import skypro.TeamWorkTelegramBot.controller.VolunteerController;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.rest.AnimalOwnerService;
import skypro.TeamWorkTelegramBot.service.rest.ReportService;
import skypro.TeamWorkTelegramBot.service.rest.ShelterService;
import skypro.TeamWorkTelegramBot.service.rest.VolunteerService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class TeamWorkTelegramBotApplicationTests {
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

	@SneakyThrows
	@Test
	void getAllVolunteersFromAnimalOwner() {
		AnimalOwner animalOwner1 = new AnimalOwner(1, 1L, "contact", true,
				false, false, false, true, false,
				false,false);

		AnimalOwner animalOwner2 = new AnimalOwner(2, 2L, "contact", true,
				false, false, false, true, false,
				false,false);

		List<AnimalOwner> animalOwnerList = new ArrayList<>();
		animalOwnerList.add(animalOwner1);
		animalOwnerList.add(animalOwner2);

		when(animalOwnerService.getAllVolunteers()).thenReturn(animalOwnerList);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/animal-owner")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void getReportPhoto() {

		Integer id = 1;
		LocalDateTime date = LocalDateTime.now();
		String reportText = "testReport";
		String telegramFieldId = "testFileId";
		Integer fileSize = 10;

		Report report = Report.builder()
				.id(id)
				.date(date)
				.report(reportText)
				.telegramFieldId(telegramFieldId)
				.fileSize(fileSize)
				.build();

		when(reportService.getPhoto(any(Integer.class))).thenReturn(report);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/animal-report/get-report-photo?reportId=1")
				.accept(MediaType.APPLICATION_JSON));
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

		when(reportService.getAllReportsByPages(0,2)).thenReturn(reportList);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/animal-report/all-reports-by-pages?page=1&size=1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void addShelter() {
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
	void findShelter() {
		Shelter shelter = new Shelter(1,"testShelter");
		when(shelterService.findShelter(1)).thenReturn(shelter);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/animal-shelter/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void getAllShelters() {
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
	void editShelter() {
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
	void removeShelter() {
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

	@SneakyThrows
	@Test
	void getShelterAnimalOwners() {
		Shelter shelter = new Shelter(1,"TestName");
		List<AnimalOwner> animalOwnersList = new ArrayList<>();
		AnimalOwner animalOwner1 = new AnimalOwner(1, 1L, "contact", true,
				false, false, false, true, false,
				false,false);
		AnimalOwner animalOwner2 = new AnimalOwner(2, 2L, "contact", true,
				false, false, false, true, false,
				false,false);
		animalOwner1.setShelter(shelter);
		animalOwner2.setShelter(shelter);
		animalOwnersList.add(animalOwner1);
		animalOwnersList.add(animalOwner2);
		shelter.setAnimalOwners(animalOwnersList);

		when(sheltersRepository.findById(anyInt())).thenReturn(Optional.of(shelter));

		mockMvc.perform(MockMvcRequestBuilders
						.get("/animal-shelter/animal-owners-by-id/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void addVolunteer() {
		Volunteer volunteer = new Volunteer(1,1L,"testName",false);

		JSONObject volunteerObject = new JSONObject();
		volunteerObject.put("id", "1");
		volunteerObject.put("idChat", "1");
		volunteerObject.put("name","TestName");
		volunteerObject.put("isBusy","false");


		when(volunteerService.findVolunteer(1)).thenReturn(volunteer);
		when(volunteerService.addVolunteer(volunteer)).thenReturn(volunteer);
		when(volunteersRepository.save(volunteer)).thenReturn(volunteer);

		mockMvc.perform(MockMvcRequestBuilders
						.post("/volunteer")
						.content(volunteerObject.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void getAllVolunteers() {
		Volunteer volunteer = new Volunteer(1,1L,"testName",false);
		Volunteer volunteer2 = new Volunteer(2,2L,"testName2",false);

		AnimalOwner animalOwner = new AnimalOwner(1, 123L, "+7", true,
				true, false, false, true, false,
				false, false, volunteer,
				Shelter.builder()
						.id(1)
						.name("Alex")
						.build());


		List<Volunteer> volunteerList = new ArrayList<>();
		volunteerList.add(volunteer);
		volunteerList.add(volunteer2);

		List<AnimalOwner> animalOwners = new ArrayList<>();
		animalOwners.add(animalOwner);

		when(volunteerService.getAllVolunteers()).thenReturn(volunteerList);
		when(animalOwnerRepository.findByIsVolunteer()).thenReturn(animalOwners);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/volunteer")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void findVolunteer() {
		Volunteer foundedVolunteer = new Volunteer(1,1L,"testName",false);

		when(volunteerService.findVolunteer(1)).thenReturn(foundedVolunteer);

		mockMvc.perform(MockMvcRequestBuilders
						.get("/volunteer/1")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@SneakyThrows
	@Test
	void editVolunteer() {
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
	void removeVolunteer() {
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
