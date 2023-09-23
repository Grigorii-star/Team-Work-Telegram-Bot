package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.DateAndTimeReport;

/**
 * Интерфейс для доступа к датам отчетов о животных DateAndTimeReport в БД.
 */
public interface DateAndTimeReportRepository extends JpaRepository<DateAndTimeReport, Integer> {
    /**
     * Метод, который выгружает из БД даты отчетов о питомце по Telegram chatId пользователя.
     * @param idChatAnimalOwner Telegram chatId пользователя.
     * @return даты отчетов, найденные по указанному идентификатору.
     */
    DateAndTimeReport findByIdChatAnimalOwner(Long idChatAnimalOwner);
}
