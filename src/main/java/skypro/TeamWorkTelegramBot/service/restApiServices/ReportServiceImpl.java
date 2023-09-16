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
    public Report addAnimal(Report report) {
//        log.info("Invoked a method for adding a animal");
//
//        Animal animalExistsCheck = animalsRepository.findByName(animal.getName());
//        if (animalExistsCheck != null && animalExistsCheck.getName().equals(animal.getName())) {
//            log.error("The animal {} already exists", animalExistsCheck);
//        }
//
//        Animal addedAnimal = animalsRepository.save(animal);
//        log.debug("Animal {} was added", addedAnimal);
        return null;
    }

    @Override
    public Report findAnimal(Integer id) {
        log.info("Invoked a method for finding animal");

        animalIdValidation(id);

        Report foundReport = reportsRepository.findById(id).get();
        log.debug("Animal with id {} was found", id);
        return foundReport;
    }

    @Override
    public Report editAnimal(Report report) {
        log.info("Invoked a method for updating a animal");

        animalIdValidation(report.getId());

        Report updatedReport = reportsRepository.save(report);
        log.debug("Animal {} was updated", updatedReport);
        return updatedReport;
    }

    @Override
    public void removeAnimal(Integer id) {
        log.info("Invoked a method for removing a animal");

        animalIdValidation(id);

        log.debug("Animal with id {} was removed", id);
        reportsRepository.deleteById(id);
    }

    @Override
    public Collection<Report> getAllAnimals() {
        log.info("Invoked a method for getting all animals");

        log.debug("All animals were received");
        return Collections.unmodifiableCollection(reportsRepository.findAll());
    }

    private void animalIdValidation(Integer id) {
        if (!reportsRepository.existsById(id)) {
            log.error("There is no animal with id = {}", id);
        }
    }
}
