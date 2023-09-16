package skypro.TeamWorkTelegramBot.service.restApiServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * класс реализации интерфейса ShelterService
 */
@Service
@Slf4j
public class ShelterServiceImpl implements ShelterService{
    private final SheltersRepository sheltersRepository;

    public ShelterServiceImpl(SheltersRepository sheltersRepository) {
        this.sheltersRepository = sheltersRepository;
    }

    @Override
    public Shelter addShelter(Shelter shelter) {
        log.info("Invoked a method for adding a shelter");

        Shelter shelterExistsCheck = sheltersRepository.findByName(shelter.getName());
        if (shelterExistsCheck != null && shelterExistsCheck.getName().equals(shelter.getName())) {
            log.error("The shelter {} already exists", shelterExistsCheck);
        }

        Shelter addedShelter = sheltersRepository.save(shelter);
        log.debug("Shelter {} was added", addedShelter);
        return addedShelter;
    }

    @Override
    public Shelter findShelter(Integer id) {
        log.info("Invoked a method for finding shelter");

        shelterIdValidation(id);

        Shelter foundShelter = sheltersRepository.findById(id).get();
        log.debug("Shelter with id {} was found", id);
        return foundShelter;
    }

    @Override
    public Shelter editShelter(Shelter shelter) {
        log.info("Invoked a method for updating a shelter");

        shelterIdValidation(shelter.getId());

        Shelter updatedShelter = sheltersRepository.save(shelter);
        log.debug("Shelter {} was updated", updatedShelter);
        return updatedShelter;
    }

    @Override
    public void removeShelter(Integer id) {
        log.info("Invoked a method for removing a shelter");

        shelterIdValidation(id);

        log.debug("Shelter with id {} was removed", id);
        sheltersRepository.deleteById(id);
    }

    @Override
    public Collection<Shelter> getAllShelters() {
        log.info("Invoked a method for getting all shelters");

        log.debug("All volunteers were received");
        return Collections.unmodifiableCollection(sheltersRepository.findAll());
    }

    private void shelterIdValidation(Integer id) {
        if (!sheltersRepository.existsById(id)) {
            log.error("There is no shelter with id = {}", id);
        }
    }
}
