package skypro.TeamWorkTelegramBot.service.restApiServices;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.dto.ReportDTO;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Класс реализации интерфейса AnimalService
 */
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportsRepository reportsRepository;

    public ReportServiceImpl(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    @Override
    public Report findReport(Integer reportId) {
        log.info("Invoked a method for finding report by report id");

        return reportsRepository.findById(reportId).orElse(null);
    }

//    public Report findReport2(Integer reportId) {
//        log.info("Invoked a method for finding report by user id");
//
//        Report foundedReport = reportsRepository.findByAnimalOwnerId(reportId).orElse(new Report());
//        log.debug("Report was with animal owner id {} founded", reportId);
//        return foundedReport;
//    }


    @Override
    public List<Report> getAllReportsByPages(Integer pageNumber, Integer pageSize) {
        log.info("Invoked a method for getting all reports by pages");

        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return reportsRepository.findAll(pageRequest).getContent();
    }

    @Override
    public List<ReportDTO> getAllReportsByUserId(Integer userId) {
        log.info("Invoked a method for getting all reports");

        log.debug("All reports were received");
        return reportsRepository.findByAnimalOwnerId(userId);
    }

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

    private void reportIdValidation(Integer reportId) {
        if (!reportsRepository.existsById(reportId)) {
            log.error("There is no report with id = {}", reportId);
        }
    }
}
