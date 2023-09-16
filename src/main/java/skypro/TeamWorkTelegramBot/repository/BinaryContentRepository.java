package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, Integer> {
}
