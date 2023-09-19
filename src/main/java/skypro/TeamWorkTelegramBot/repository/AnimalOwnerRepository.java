package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

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
}
