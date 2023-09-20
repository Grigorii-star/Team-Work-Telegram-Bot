package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;

/**
 * Интерфейс для доступа к данным фотографии BinaryContent в БД.
 */
public interface BinaryContentRepository extends JpaRepository<BinaryContent, Integer> {
}
