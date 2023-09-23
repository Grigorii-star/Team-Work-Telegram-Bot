package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

import java.util.List;

/**
 * Интерфейс для доступа к данным пользователей AnimalOwner в БД.
 */
public interface AnimalOwnerRepository extends JpaRepository<AnimalOwner, Integer> {
    /**
     * Метод для поиска владельца животного по chatId Telegram бота.
     *
     * @param idChat Telegram бота.
     * @return объект AnimalOwner из БД.
     */
    AnimalOwner findByIdChat(Long idChat);

    /**
     * Метод для поиска волонтеров.
     *
     * @return список AnimalOwner с Boolean значением isVolunteer = true из БД.
     */
    @Query(value = "SELECT * FROM animal_owner a WHERE a.is_volunteer = true", nativeQuery = true)
    List<AnimalOwner> findByIsVolunteer();
}
