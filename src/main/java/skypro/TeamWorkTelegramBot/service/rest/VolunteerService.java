package skypro.TeamWorkTelegramBot.service.rest;

import skypro.TeamWorkTelegramBot.entity.Volunteer;

import java.util.Collection;

/**
 * Интерфейс для rest запросов к БД с волонтерами.
 */
public interface VolunteerService {

    /**
     * Метод добавления волонтера в базу данных.
     *
     * @param volunteer Волонтер для добавления.
     * @return Добавленный волонтер.
     */
    Volunteer addVolunteer(Volunteer volunteer);

    /**
     * Метод который находит волонтера по указанному идентификатору.
     *
     * @param id Идентификатор волонтера для поиска.
     * @return Волонтер, найденный по указанному идентификатору
     */
    Volunteer findVolunteer(Integer id);

    /**
     * Метод редактирования волонтера в базе данных.
     *
     * @param volunteer Волонтер для редактирования.
     * @return отредактированный волонтер.
     */
    Volunteer editVolunteer(Volunteer volunteer);

    /**
     * Метод удаляет волонтера из базы данных.
     *
     * @param id Идентификатор волонтера для поиска.
     */
    void removeVolunteer(Integer id);

    /**
     * Метод получить всех волонтеров из базы данных.
     */
    Collection<Volunteer> getAllVolunteers();
}
