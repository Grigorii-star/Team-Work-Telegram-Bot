package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.DateAndTimeReport;

public interface DateAndTimeReportRepository extends JpaRepository<DateAndTimeReport, Integer> {
    DateAndTimeReport findByIdChatAnimalOwner(Long idChatAnimalOwner);
}
