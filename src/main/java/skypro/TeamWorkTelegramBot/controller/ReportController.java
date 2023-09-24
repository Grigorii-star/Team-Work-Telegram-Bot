package skypro.TeamWorkTelegramBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.service.rest.ReportService;

import java.util.List;

/**
 * REST API для запросов к БД с отчетами владельцев питомцев.
 */
@Slf4j
@RestController
@RequestMapping("animal-report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Метод, который выгружает из БД фото питомца по id отчета.
     *
     * @param reportId идентификатор id отчета.
     * @return ResponseEntity с найденным фото отчета.
     */
    @Operation(
            summary = "ВЫГРУЗИТЬ ФОТО ПИТОМЦА ПО id ОТЧЕТА",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Фото питомца успешно найдено"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Фото питомца не найдено"
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при поиске фото питомца"
                    )
            },
            tags = "Report about pet controller"
    )
    @RequestMapping(method = RequestMethod.GET, value = "/get-report-photo")
    public ResponseEntity<?> getReportPhoto(@Parameter(description = "Номер id отчета о питомце")
                                            @RequestParam("reportId") Integer reportId) {

        var photo = reportService.getPhoto(reportId);
        if (photo == null) {
            return ResponseEntity.badRequest().build();
        }

        var binaryContent = photo.getBinaryContent();
        var fileSystemResource = reportService.getFileSystemResource(binaryContent);
        if (fileSystemResource == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-disposition", "attachment; filename=")
                .body(fileSystemResource);
    }

    /**
     * Метод, который получает список всех отчетов о животных постранично.
     *
     * @param pageNumber количество страниц.
     * @param pageSize   размер страницы.
     * @return ResponseEntity с коллекцией объектов Report.
     */
    @Operation(
            summary = "ПОЛУЧИТЬ ВСЕ ОТЧЕТЫ О ПИТОМЦАХ ПОСТРАНИЧНО",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Список отчетов о питомцах успешно получен",
                            content = @Content(
                                    schema = @Schema(implementation = Report.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Отчеты о питомцах не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при получении списка отчетов о питомцах",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Report about pet controller"
    )
    @GetMapping("/all-reports-by-pages")
    public ResponseEntity<List<Report>> getReportsByPages(@Parameter(description = "Количество страниц")
                                                          @RequestParam("page") Integer pageNumber,
                                                          @Parameter(description = "Количество отчетов на странице")
                                                          @RequestParam("size") Integer pageSize) {
        return ResponseEntity.ok(reportService.getAllReportsByPages(pageNumber, pageSize));
    }
}
