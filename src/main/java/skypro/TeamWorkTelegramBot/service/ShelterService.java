package skypro.TeamWorkTelegramBot.service;

import skypro.TeamWorkTelegramBot.entity.Shelter;

import java.util.Collection;

/**
 * Интерфейс приюта
 */
public interface ShelterService {
    /**
     * Добавляет приют в базу данных.
     * @param shelter Приют для добавления.
     * @return Добавленный приют.
     */
    Shelter addShelter(Shelter shelter);
    /**
     * метод который находит приют по указанному идентификатору.
     * @param id Идентификатор приюта для поиска.
     * @return приют, найденный по указанному идентификатору
     */

    Shelter findShelter(Long id);
    /**
     * метод редактирования приюта в базе данных.
     * @param shelter Приют для редактирования.
     * @return отредактированный приют.
     */
    Shelter editShelter(Shelter shelter);
    /**
     * метод удаляет приют из базы данных.
     * @param id Идентификатор приюта для поиска.
     * @return Удаленный приют.
     */
    void removeShelter(Long id);
    /**
     * метод получить все приюты из базы данных.
     */
    Collection<Shelter> getAllShelters();
}
