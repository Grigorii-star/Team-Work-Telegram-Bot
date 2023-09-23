package skypro.TeamWorkTelegramBot.service.rest;

import org.springframework.core.io.FileSystemResource;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.entity.Report;

import java.util.List;

/**
 * Интерфейс для rest запросов к БД с отчетами владельцев питомцев.
 */
public interface ReportService {

    /**
     * Метод, который выгружает из БД отчет о питомце по id отчета.
     *
     * @param reportId Идентификатор отчета для поиска.
     * @return отчет, найденный по указанному идентификатору.
     */
    Report getPhoto(Integer reportId);

    /**
     * Метод выгружает из БД все отчеты постранично.
     *
     * @param pageNumber количество страниц.
     * @param pageSize размер страницы.
     * @return список найденных отчетов.
     */
    List<Report> getAllReportsByPages(Integer pageNumber, Integer pageSize);

    /**
     * Метод для выгрузки фото из БД бинарного кода.
     *
     * @param binaryContent закодированный объект.
     * @return выгруженный файл.
     */
    FileSystemResource getFileSystemResource(BinaryContent binaryContent);
}
