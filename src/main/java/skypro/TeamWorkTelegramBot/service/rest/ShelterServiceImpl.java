package skypro.TeamWorkTelegramBot.service.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.repository.SheltersRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс реализации интерфейса ShelterService.
 */
@Slf4j
@Service
public class ShelterServiceImpl implements ShelterService{
    private final SheltersRepository sheltersRepository;

    public ShelterServiceImpl(SheltersRepository sheltersRepository) {
        this.sheltersRepository = sheltersRepository;
    }

    /**
     * Добавляет приют в базу данных.
     * @param shelter приют для добавления.
     * @return добавленный приют.
     */
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

    /**
     * Метод который находит приют по указанному идентификатору.
     *
     * @param id идентификатор приюта для поиска.
     * @return приют, найденный по указанному идентификатору
     */
    @Override
    public Shelter findShelter(Integer id) {
        log.info("Invoked a method for finding shelter");

        shelterIdValidation(id);

        Shelter foundShelter = sheltersRepository.findById(id).get();
        log.debug("Shelter with id {} was found", id);
        return foundShelter;
    }

    /**
     * Метод редактирования приюта в базе данных.
     *
     * @param shelter приют для редактирования.
     * @return отредактированный приют.
     */
    @Override
    public Shelter editShelter(Shelter shelter) {
        log.info("Invoked a method for updating a shelter");

        shelterIdValidation(shelter.getId());

        Shelter updatedShelter = sheltersRepository.save(shelter);
        log.debug("Shelter {} was updated", updatedShelter);
        return updatedShelter;
    }

    /**
     * Метод удаляет приют из базы данных.
     *
     * @param id идентификатор приюта для поиска.
     */
    @Override
    public void removeShelter(Integer id) {
        log.info("Invoked a method for removing a shelter");

        shelterIdValidation(id);

        log.debug("Shelter with id {} was removed", id);
        sheltersRepository.deleteById(id);
    }

    /**
     * Метод получить все приюты из базы данных.
     * @return список всех приютов.
     */
    @Override
    public Collection<Shelter> getAllShelters() {
        log.info("Invoked a method for getting all shelters");

        log.debug("All volunteers were received");
        return Collections.unmodifiableCollection(sheltersRepository.findAll());
    }

    /**
     * Метод получает всех владельцев животных из базы данных.
     * @return список всех владельцев животных.
     */
    @Override
    public Collection<AnimalOwner> getShelterAnimalOwners(Integer id) {
        log.info("Invoked a method for getting all the shelter's animal owners");

        shelterIdValidation(id);
        Collection<AnimalOwner> animalOwnersExistsCheck = sheltersRepository.findById(id)
                .map(Shelter::getAnimalOwners)
                .orElse(null);
        if (animalOwnersExistsCheck != null && animalOwnersExistsCheck.isEmpty()) {
            log.error("Shelter with id {} hasn't any animal owners", id);
        }

        log.debug("Animal owners of shelter with id {} were received", id);
        return animalOwnersExistsCheck;
    }

    /**
     * Метод проверяет наличие приюта в БД по идентификатору.
     * @param id идентификатор приюта.
     */
    private void shelterIdValidation(Integer id) {
        if (!sheltersRepository.existsById(id)) {
            log.error("There is no shelter with id = {}", id);
        }
    }
}
