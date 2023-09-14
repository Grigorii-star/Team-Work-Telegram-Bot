package skypro.TeamWorkTelegramBot.service;

import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.Animal;

import java.util.Collection;

/**
 * Класс реализации интерфейса AnimalService
 */
@Service
public class AnimalServiceImpl implements AnimalService{
    @Override
    public Animal addAnimal(Animal animal) {
        return null;
    }

    @Override
    public Animal findAnimal(Long id) {
        return null;
    }

    @Override
    public Animal editAnimal(Animal animal) {
        return null;
    }

    @Override
    public void removeAnimal(Long id) {

    }

    @Override
    public Collection<Animal> getAllAnimals() {
        return null;
    }
}
