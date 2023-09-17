package skypro.TeamWorkTelegramBot.service.restApiServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс реализации интерфейса AnimalService
 */
@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    private final ReportsRepository reportsRepository;

    public ReportServiceImpl(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    @Override
    public Report findReport(Integer id) {
        log.info("Invoked a method for finding report");

        reportIdValidation(id);

        Report foundReport = reportsRepository.findById(id).get();
        log.debug("Report with id {} was found", id);
        return foundReport;
    }

    @Override
    public Collection<Report> getAllReports() {
        log.info("Invoked a method for getting all reports");

        log.debug("All reports were received");
        return Collections.unmodifiableCollection(reportsRepository.findAll());
    }

    private void reportIdValidation(Integer id) {
        if (!reportsRepository.existsById(id)) {
            log.error("There is no report with id = {}", id);
        }
    }
}
