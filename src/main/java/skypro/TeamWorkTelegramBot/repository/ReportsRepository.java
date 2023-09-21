package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.dto.ReportDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportsRepository extends JpaRepository<Report, Integer> {
    // Optional<Report> findByAnimalOwnerId(Integer userId);
    @Query(value = "SELECT * FROM report r WHERE r.animal_owner_id = ?1", nativeQuery = true)
    List<ReportDTO> findByAnimalOwnerId(Integer userId);

    //    @Query(value = "SELECT MIN(r.date) FROM Report r WHERE r.date <= current_date - interval ('30 days')")
//    LocalDateTime findByAnimalOwnerId(Long id);
    @Query(value = "SELECT MIN(r.date) FROM report r, animal_owner a WHERE a.id_chat = ?1 AND r.date = (current_date - interval '30 days')", nativeQuery = true)
    LocalDateTime findDateByAnimalOwnerId(Long chatId);
}
