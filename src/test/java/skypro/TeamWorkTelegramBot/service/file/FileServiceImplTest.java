package skypro.TeamWorkTelegramBot.service.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.BinaryContentRepository;
import skypro.TeamWorkTelegramBot.repository.DateAndTimeReportRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    ReportsRepository reportsRepository;
    @Mock
    BinaryContentRepository binaryContentRepository;
    @Mock
    AnimalOwnerRepository animalOwnerRepository;
    @Mock
    DateAndTimeReportRepository dateAndTimeReportRepository;

    private FileService out;

    @BeforeEach
    public void setUp() {
        out = new FileServiceImpl(reportsRepository,
                binaryContentRepository,
                animalOwnerRepository,
                dateAndTimeReportRepository);
    }

    @Test
    void animalReport() {
        User user = new User(37909760L, "Том", false, "Ридл", "RiddleTom",
                "ru", null, null, null);
        Chat chat = new Chat(37909760L,"private", null, "Том", "Ридл", "RiddleTom",
                null, null, null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);
        PhotoSize photoSize1 = new PhotoSize("AgACAgIAAxkBAAISAWUQC_QKjLgcCBhVR5NPe7evS3TFAAL9yTEbiDd4SNQBc2sjdpVhAQADAgADeAADMAQ",
                "AQAD_ckxG4g3eEh9", 800, 500,78313,null);
        PhotoSize photoSize2 = new PhotoSize("AgACAgIAAxkBAAISAWUQC_QKjLgcCBhVR5NPe7evS3TFAAL9yTEbiDd4SNQBc2sjdpVhAQADAgADeAADMAQ",
                "AQAD_ckxG4g3eEh9", 800, 500,78313,null);
        PhotoSize photoSize3 = new PhotoSize("AgACAgIAAxkBAAISAWUQC_QKjLgcCBhVR5NPe7evS3TFAAL9yTEbiDd4SNQBc2sjdpVhAQADAgADeAADMAQ",
                "AQAD_ckxG4g3eEh9", 800, 500,78313,null);
        PhotoSize photoSize4 = new PhotoSize("AgACAgIAAxkBAAISAWUQC_QKjLgcCBhVR5NPe7evS3TFAAL9yTEbiDd4SNQBc2sjdpVhAQADAgADeAADMAQ",
                "AQAD_ckxG4g3eEh9", 800, 500,78313,null);
        List<PhotoSize> photoSizeList = new ArrayList<>();
        photoSizeList.add(photoSize1);
        photoSizeList.add(photoSize2);
        photoSizeList.add(photoSize3);
        photoSizeList.add(photoSize4);

        Message message = new Message(4609, user, 1695550452, chat, null, null, null, null, null, null, null, null,
                photoSizeList, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                "Report", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        Integer id = 1;
        LocalDateTime date = LocalDateTime.now();
        String report = "Report";
        String fileId = "TelegramFieldId";
        Integer fileSize = 94884;

        Report transientReport = new Report(id, date, report, fileId, fileSize);
        when(reportsRepository.save(transientReport)).thenReturn(transientReport);
//        verify(reportsRepository.save(transientReport));
//        assertEquals(transientReport, out.animalReport(message));
        assertEquals(transientReport, out.animalReport(any(Message.class)));
        verify(reportsRepository, only()).save(transientReport);
    }
}