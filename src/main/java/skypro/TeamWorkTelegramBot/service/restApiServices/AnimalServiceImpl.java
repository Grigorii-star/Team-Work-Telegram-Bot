package skypro.TeamWorkTelegramBot.service.restApiServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.Animal;
import skypro.TeamWorkTelegramBot.repository.AnimalsRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс реализации интерфейса AnimalService
 */
@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService{
    private final AnimalsRepository animalsRepository;

    public AnimalServiceImpl(AnimalsRepository animalsRepository) {
        this.animalsRepository = animalsRepository;
    }

    @Override
    public Animal addAnimal(Animal animal) {
        log.info("Invoked a method for adding a animal");

        Animal animalExistsCheck = animalsRepository.findByName(animal.getName());
        if (animalExistsCheck != null && animalExistsCheck.getName().equals(animal.getName())) {
            log.error("The animal {} already exists", animalExistsCheck);
        }

        Animal addedAnimal = animalsRepository.save(animal);
        log.debug("Animal {} was added", addedAnimal);
        return addedAnimal;
    }

    @Override
    public Animal findAnimal(Integer id) {
        log.info("Invoked a method for finding animal");

        animalIdValidation(id);

        Animal foundAnimal = animalsRepository.findById(id).get();
        log.debug("Animal with id {} was found", id);
        return foundAnimal;
    }

    @Override
    public Animal editAnimal(Animal animal) {
        log.info("Invoked a method for updating a animal");

        animalIdValidation(animal.getId());

        Animal updatedAnimal = animalsRepository.save(animal);
        log.debug("Animal {} was updated", updatedAnimal);
        return updatedAnimal;
    }

    @Override
    public void removeAnimal(Integer id) {
        log.info("Invoked a method for removing a animal");

        animalIdValidation(id);

        log.debug("Animal with id {} was removed", id);
        animalsRepository.deleteById(id);
    }

    @Override
    public Collection<Animal> getAllAnimals() {
        log.info("Invoked a method for getting all animals");

        log.debug("All animals were received");
        return Collections.unmodifiableCollection(animalsRepository.findAll());
    }

    private void animalIdValidation(Integer id) {
        if (!animalsRepository.existsById(id)) {
            log.error("There is no animal with id = {}", id);
        }
    }
}
