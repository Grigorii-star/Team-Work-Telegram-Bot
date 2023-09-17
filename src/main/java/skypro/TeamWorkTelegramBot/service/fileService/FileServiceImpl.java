package skypro.TeamWorkTelegramBot.service.fileService;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.exception.UploadFileException;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.repository.BinaryContentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

@Slf4j
@Service
public class FileServiceImpl implements FileService{
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${service.file_info.uri}")
    private String fileInfoUri;
    @Value("${service.file_storage.uri}")
    private String fileStorageUri;

    private final ReportsRepository reportsRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final AnimalOwnerRepository animalOwnerRepository;

    public FileServiceImpl(ReportsRepository reportsRepository,
                           BinaryContentRepository binaryContentRepository,
                           AnimalOwnerRepository animalOwnerRepository) {
        this.reportsRepository = reportsRepository;
        this.binaryContentRepository = binaryContentRepository;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    @Override
    public Report animalReport(Message telegramMessage) {
        PhotoSize telegramPhoto = telegramMessage.getPhoto().get(0);
        String fileId = telegramPhoto.getFileId();
        Long chatId = telegramMessage.getChatId();
        String report = telegramMessage.getCaption();
        LocalDate date = LocalDate.now();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        ResponseEntity<String> response = getFilePath(fileId);
        if (response.getStatusCode() == HttpStatus.OK) {
            BinaryContent persistentBinaryContent = getBinaryContent(response);
            Report transientReport = buildTransientAnimal(telegramPhoto,
                                                        persistentBinaryContent,
                                                        animalOwner,
                                                        report, date);
            return reportsRepository.save(transientReport);
        } else {
            throw new UploadFileException("Bad response from telegram service: " + response);
        }
    }

    private Report buildTransientAnimal(PhotoSize telegramPhoto,
                                        BinaryContent binaryContent,
                                        AnimalOwner animalOwner,
                                        String report,
                                        LocalDate date) {
        return Report.builder()
                .telegramFieldId(telegramPhoto.getFileId())
                .binaryContent(binaryContent)
                .fileSize(telegramPhoto.getFileSize())
                .animalOwner(animalOwner)
                .report(report)
                .date(date)
                .build();
    }

    private ResponseEntity<String> getFilePath(String fileId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                fileInfoUri,
                HttpMethod.GET,
                request,
                String.class,
                token,
                fileId
        );
    }

    private BinaryContent getBinaryContent(ResponseEntity<String> response) {
        String filePath = getFilePath(response);
        byte[] fileInByte = downloadFile(filePath);
        BinaryContent transientBinaryContent = BinaryContent.builder()
                .data(fileInByte)
                .build();
        return binaryContentRepository.save(transientBinaryContent);
    }

    private String getFilePath(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"));
    }

    private byte[] downloadFile(String filePath) {
        String fullUri = fileStorageUri.replace("{telegram.bot.token}", token)
                .replace("{filePath}", filePath);
        URL urlObj = null;
        try {
            urlObj = new URL(fullUri);
        } catch (MalformedURLException e) {
            throw new UploadFileException(e);
        }

        try (InputStream is = urlObj.openStream()){
            return is.readAllBytes();
        } catch (IOException e) {
            throw new UploadFileException(urlObj.toExternalForm(), e);
        }
    }
}
