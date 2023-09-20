package skypro.TeamWorkTelegramBot.service.rest;

import skypro.TeamWorkTelegramBot.entity.Shelter;

import java.util.Collection;

/**
 * Интерфейс для rest запросов к БД с приютами питомцев.
 */
public interface ShelterService {

    /**
     * Добавляет приют в базу данных.
     * @param shelter приют для добавления.
     * @return добавленный приют.
     */
    Shelter addShelter(Shelter shelter);

    /**
     * Метод который находит приют по указанному идентификатору.
     *
     * @param id идентификатор приюта для поиска.
     * @return приют, найденный по указанному идентификатору
     */
    Shelter findShelter(Integer id);

    /**
     * Метод редактирования приюта в базе данных.
     *
     * @param shelter приют для редактирования.
     * @return отредактированный приют.
     */
    Shelter editShelter(Shelter shelter);

    /**
     * Метод удаляет приют из базы данных.
     *
     * @param id идентификатор приюта для поиска.
     */
    void removeShelter(Integer id);

    /**
     * Метод получить все приюты из базы данных.
     * @return список всех приютов.
     */
    Collection<Shelter> getAllShelters();
}
