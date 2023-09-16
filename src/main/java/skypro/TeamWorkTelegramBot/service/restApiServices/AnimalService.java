package skypro.TeamWorkTelegramBot.service.restApiServices;

import skypro.TeamWorkTelegramBot.entity.Animal;

import java.util.Collection;

/**
 *Интерфейс для животных
 */
public interface AnimalService {
    /**
     * Добавляет животного в базу данных.
     * @param animal животное для добавления.
     * @return Добавленное животное.
     */
    Animal addAnimal(Animal animal);
    /**
     * метод который находит животного по указанному идентификатору.
     * @param id Идентификатор животного для поиска.
     * @return животное, найденное по указанному идентификатору
     */
    Animal findAnimal(Integer id);
    /**
     * метод редактирования животного в базе данных.
     * @param animal животноое для редактирования.
     * @return отредактированное животное.
     */
    Animal editAnimal(Animal animal);
    /**
     * метод удаляет животного из базы данных.
     * @param id Идентификатор животного для поиска.
     * @return удаленное животное.
     */
    void removeAnimal(Integer id);
    /**
     * метод получить всех животных из базы данных.
     */
    Collection<Animal> getAllAnimals();
}
