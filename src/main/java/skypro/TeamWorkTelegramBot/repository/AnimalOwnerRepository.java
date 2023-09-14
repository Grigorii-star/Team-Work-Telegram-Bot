package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

/**
 *Этот интерфейс используется для доступа к данным AnimalOwner в базе данных.
 */
public interface AnimalOwnerRepository extends JpaRepository<AnimalOwner, Integer> {
    /**
     * метод для поиска владельца животного по chatId телеграмм бота
     * @param chatId id телеграмм бота
     * @return
     */
    AnimalOwner findByIdChat(Long chatId);
}
