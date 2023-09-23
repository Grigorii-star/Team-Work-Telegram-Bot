package skypro.TeamWorkTelegramBot.service.file;

import org.junit.jupiter.api.BeforeAll;;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileServiceImplTest {

    @LocalServerPort
    private int port;

    @Mock
    AnimalOwnerRepository animalOwnerRepository;
    @Mock
    ReportsRepository reportsRepository;

    @BeforeAll
    static void init() {
    }

    @Test
    void animalReport() {

        String fileId = "AgACAgQAAxkBAAIPcGUMuCAQpHePkkjWv9JriSSXfKFFAAJ9vTEbSa5hUDOPMuYiw5-4AQADAgADcwADMAQ";
        Long chatId = 1L;
        String report = "Report";
        LocalDateTime date = LocalDateTime.now();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        Integer binaryContent = 58126;

        Report transientReport = new Report(1, date, report, fileId, binaryContent);

        when(animalOwnerRepository.save(animalOwner)).thenReturn(animalOwner);
        when(reportsRepository.save(transientReport)).thenReturn(transientReport);
    }
}