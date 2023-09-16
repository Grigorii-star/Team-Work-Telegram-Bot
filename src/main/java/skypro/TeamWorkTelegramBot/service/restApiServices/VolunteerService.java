package skypro.TeamWorkTelegramBot.service.restApiServices;

import skypro.TeamWorkTelegramBot.entity.Volunteer;

import java.util.Collection;

/**
 * интерфейс волонтера
 */
public interface VolunteerService {
    /**
     * метод добавления волонтера в базу данных.
     * @param volunteer Волонтер для добавления.
     * @return Добавленный волонтер.
     */
    Volunteer addVolunteer(Volunteer volunteer);

    /**
     * метод который находит волонтера по указанному идентификатору.
     * @param id Идентификатор волонтера для поиска.
     * @return Волонтер, найденный по указанному идентификатору
     */
    Volunteer findVolunteer(Integer id);

    /**
     * метод редактирования волонтера в базе данных.
     * @param volunteer Волонтер для редактирования.
     * @return отредактированный волонтер.
     */

    Volunteer editVolunteer(Volunteer volunteer);
    /**
     * метод удаляет волонтера из базы данных.
     * @param id Идентификатор волонтера для поиска.
     * @return удаленный волонтер.
     */
    void removeVolunteer(Integer id);
    /**
     * метод получить всех волонтеров из базы данных.
     */
    Collection<Volunteer> getAllVolunteers();
}
