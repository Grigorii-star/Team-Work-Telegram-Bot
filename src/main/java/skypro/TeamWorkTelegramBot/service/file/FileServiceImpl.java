package skypro.TeamWorkTelegramBot.service.file;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import skypro.TeamWorkTelegramBot.entity.DateAndTimeReport;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.exception.UploadFileException;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.DateAndTimeReportRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.repository.BinaryContentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * Класс осуществляет сохранение отчетов о животном в БД.
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService{
    /**
     * Переменная содержит токен Telegram бота.
     */
    @Value("${telegram.bot.token}")
    private String token;
    /**
     * Переменная содержит адрес Telegram расположения файла.
     */
    @Value("${service.file_info.uri}")
    private String fileInfoUri;
    /**
     * Переменная содержит адрес Telegram по которому можно скачать файл.
     */
    @Value("${service.file_storage.uri}")
    private String fileStorageUri;

    private final ReportsRepository reportsRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final DateAndTimeReportRepository dateAndTimeReportRepository;

    public FileServiceImpl(ReportsRepository reportsRepository,
                           BinaryContentRepository binaryContentRepository,
                           AnimalOwnerRepository animalOwnerRepository,
                           DateAndTimeReportRepository dateAndTimeReportRepository) {
        this.reportsRepository = reportsRepository;
        this.binaryContentRepository = binaryContentRepository;
        this.animalOwnerRepository = animalOwnerRepository;
        this.dateAndTimeReportRepository = dateAndTimeReportRepository;
    }

    /**
     * Метод обрабатывает Message сообщения, достает из них значения фото, fieldId, chatId
     * и подпись. Добавляет текущую дату и создает новый объект отчета Report.
     * После чего сохраняет его в БД.
     *
     * @param telegramMessage - объект Telegram для получения значений из Telegram бота.
     * @return объект Report сохраненный в БД.
     */
    @Override
    public Report animalReport(Message telegramMessage) {
        PhotoSize telegramPhoto = telegramMessage.getPhoto().get(2);
        String fileId = telegramPhoto.getFileId();
        Long chatId = telegramMessage.getChatId();
        String report = telegramMessage.getCaption();
        LocalDateTime date = LocalDateTime.now();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        getDateAndTimeReport(date, chatId);

        ResponseEntity<String> response = getFilePath(fileId);
        if (response.getStatusCode() == HttpStatus.OK) {
            BinaryContent persistentBinaryContent = getBinaryContent(response);
            Report transientReport = buildTransientReport(telegramPhoto,
                    persistentBinaryContent,
                    animalOwner,
                    report,
                    date);
            return reportsRepository.save(transientReport);
        } else {
            throw new UploadFileException("Bad response from telegram service: " + response);
        }
    }

    /**
     * Метод собирает новый объект Report.
     *
     * @param telegramPhoto содержит размер фото из Telegram Message.
     * @param binaryContent содержит закодированный путь к фото файлу.
     * @param animalOwner содержит отправителя отчета.
     * @param report содержит письменный отчет.
     * @param date содержит дату отправки отчета.
     * @return собранный отчет Report.
     */
    private Report buildTransientReport(PhotoSize telegramPhoto,
                                        BinaryContent binaryContent,
                                        AnimalOwner animalOwner,
                                        String report,
                                        LocalDateTime date) {
        return Report.builder()
                .telegramFieldId(telegramPhoto.getFileId())
                .binaryContent(binaryContent)
                .fileSize(telegramPhoto.getFileSize())
                .animalOwner(animalOwner)
                .report(report)
                .date(date)
                .build();
    }

    /**
     * Метод делает Http get запрос к серверу Telegram.
     *
     * @param fileId содержит fileId фото.
     * @return HttpStatus в виде String.
     */
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

    /**
     * Метод собирает новый BinaryContent объект и сохраняет его в БД.
     *
     * @param response принимает HttpStatus от Telegram сервера в виде String.
     * @return созданный объект BinaryContent.
     */
    private BinaryContent getBinaryContent(ResponseEntity<String> response) {
        String filePath = getFilePath(response);
        byte[] fileInByte = downloadFile(filePath);
        BinaryContent transientBinaryContent = BinaryContent.builder()
                .data(fileInByte)
                .build();
        return binaryContentRepository.save(transientBinaryContent);
    }

    /**
     * Метод собирает новый DateAndTimeReport объект и сохраняет его в БД.
     * @param date содержит дату отправки отчета.
     * @param chatId содержит chatId пользователя.
     */
    private void getDateAndTimeReport(LocalDateTime date, Long chatId) {
        DateAndTimeReport reportFirst = DateAndTimeReport.builder()
                .dateActual(date)
                .dateFirst(date)
                .idChatAnimalOwner(chatId)
                .build();

        DateAndTimeReport dateReportCheck = dateAndTimeReportRepository.findByIdChatAnimalOwner(chatId);

        if (dateReportCheck != null) {
            dateReportCheck.setDateActual(date);
            dateAndTimeReportRepository.save(dateReportCheck);
        }
        else {
            dateAndTimeReportRepository.save(reportFirst);
        }
    }

    /**
     * Метод приобразует HttpStatus в виде String от Telegram
     * сервера в JSONObject, после чего преобразует его в String.
     *
     * @param response HttpStatus в виде String от Telegram сервера.
     * @return собранный из JSONObject новый String объект.
     */
    private String getFilePath(ResponseEntity<String> response) {
        JSONObject jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"));
    }

    /**
     * Метод формирует URi, после чего помещает его в URL адрес
     * для скачивания файла из Telegram хранилища. Преобразует
     * скаченный файл в бинарный код.
     *
     * @param filePath путь для скачивания файла.
     * @return вовзвращает бинарный код закаченного файла.
     */
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
