package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;

import java.util.Optional;

/**
 * Интерфейс для доступа к данным волонтеров Volunteer в БД.
 */
public interface VolunteersRepository extends JpaRepository<Volunteer, Integer> {

    /**
     * Метод для поиска волонтера по chatId Telegram бота.
     *
     * @param idChat Telegram бота.
     * @return объект Volunteer из БД.
     */
    Volunteer findByIdChat(Long idChat);

    /**
     * Метод для поиска свободного волонтера в БД.
     *
     * @param isBusy позволяет определить занят ли волонтер.
     * @return объект Volunteer из БД.
     */
    Volunteer findDistinctFirstByIsBusy(Boolean isBusy);

    /**
     * Метод для поиска волонтера по присоединенному к нему пользователю.
     *
     * @param animalOwner пердает пользователя в БД волонтеров.
     * @return объект Volunteer из БД.
     */
    Volunteer findByAnimalOwner(AnimalOwner animalOwner);
}
