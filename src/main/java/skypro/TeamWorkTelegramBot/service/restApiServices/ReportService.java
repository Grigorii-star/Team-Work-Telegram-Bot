package skypro.TeamWorkTelegramBot.service.restApiServices;

import org.springframework.core.io.FileSystemResource;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.dto.ReportDTO;

import java.util.Collection;
import java.util.List;

/**
 *Интерфейс для животных
 */
public interface ReportService {

    /**
     * метод который находит животного по указанному идентификатору.
     * @param reportId Идентификатор животного для поиска.
     * @return животное, найденное по указанному идентификатору
     */
    Report findReport(Integer reportId);

//    Report findReport2(Integer animalOwnerId);

    List<Report> getAllReportsByPages(Integer pageNumber, Integer pageSize);

    /**
     * метод получить всех животных из базы данных.
     */
    List<ReportDTO> getAllReportsByUserId(Integer userId);

    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
