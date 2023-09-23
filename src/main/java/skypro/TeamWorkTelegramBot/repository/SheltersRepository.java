package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.Shelter;

/**
 * Интерфейс для доступа к данным приютов для животных Shelter в БД.
 */
public interface SheltersRepository extends JpaRepository<Shelter, Integer> {
    /**
     * Метод для поиска приюта в БД по его названию.
     * @param name название приюта.
     * @return объект Shelter из БД.
     */
    Shelter findByName(String name);
}
