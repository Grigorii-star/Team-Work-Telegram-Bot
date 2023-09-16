package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.Report;

import java.util.Optional;

public interface ReportsRepository extends JpaRepository<Report, Integer> {
    Optional<Report> findByAnimalOwnerId(Integer userChatId);
}
