package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.dto.ReportDTO;

import java.util.List;
import java.util.Optional;

public interface ReportsRepository extends JpaRepository<Report, Integer> {
   // Optional<Report> findByAnimalOwnerId(Integer userId);
   @Query(value = "SELECT * FROM report r WHERE r.animal_owner_id = ?1", nativeQuery = true)
   List<ReportDTO> findByAnimalOwnerId(Integer userId);
}
