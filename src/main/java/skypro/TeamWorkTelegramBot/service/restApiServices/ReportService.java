package skypro.TeamWorkTelegramBot.service.restApiServices;

import skypro.TeamWorkTelegramBot.entity.Report;

import java.util.Collection;

/**
 *Интерфейс для животных
 */
public interface ReportService {

    /**
     * метод который находит животного по указанному идентификатору.
     * @param id Идентификатор животного для поиска.
     * @return животное, найденное по указанному идентификатору
     */
    Report findReport(Integer id);

    /**
     * метод получить всех животных из базы данных.
     */
    Collection<Report> getAllReports();
}
