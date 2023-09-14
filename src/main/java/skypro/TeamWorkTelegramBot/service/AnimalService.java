package skypro.TeamWorkTelegramBot.service;

import skypro.TeamWorkTelegramBot.entity.Animal;

import java.util.Collection;

public interface AnimalService {
    Animal addAnimal(Animal animal);
    Animal findAnimal(Long id);
    Animal editAnimal(Animal animal);
    void removeAnimal(Long id);
    Collection<Animal> getAllAnimals();
}
