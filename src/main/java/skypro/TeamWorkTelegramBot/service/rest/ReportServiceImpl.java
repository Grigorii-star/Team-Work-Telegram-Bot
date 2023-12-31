package skypro.TeamWorkTelegramBot.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Класс реализации интерфейса ReportService.
 */
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportsRepository reportsRepository;

    public ReportServiceImpl(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    /**
     * Метод, который выгружает из БД отчет о питомце по id отчета.
     *
     * @param reportId Идентификатор отчета для поиска.
     * @return отчет, найденный по указанному идентификатору.
     */
    @Override
    public Report getPhoto(Integer reportId) {
        log.info("Invoked a method for finding report by report id");

        return reportsRepository.findById(reportId).orElse(null);
    }

    /**
     * Метод выгружает из БД все отчеты постранично.
     *
     * @param pageNumber количество страниц.
     * @param pageSize размер страницы.
     * @return список найденных отчетов.
     */
    @Override
    public List<Report> getAllReportsByPages(Integer pageNumber, Integer pageSize) {
        log.info("Invoked a method for getting all reports by pages");

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return reportsRepository.findAll(pageRequest).getContent();
    }

    /**
     * Метод для выгрузки фото из БД бинарного кода.
     *
     * @param binaryContent закодированный объект.
     * @return выгруженный файл.
     */
    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            File tempFile = File.createTempFile("tempFile", ".bin");
            tempFile.deleteOnExit();
            FileUtils.writeByteArrayToFile(tempFile, binaryContent.getData());
            return new FileSystemResource(tempFile);
        } catch (IOException e) {
            log.error("IOException method getFileSystemResource");
            return null;
        }
    }
}
