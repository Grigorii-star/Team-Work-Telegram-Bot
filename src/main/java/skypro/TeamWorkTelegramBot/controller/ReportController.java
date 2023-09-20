package skypro.TeamWorkTelegramBot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.entity.dto.ReportDTO;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
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

    public ReportController(ReportService reportService, AnimalOwnerRepository animalOwnerRepository) {
        this.reportService = reportService;
    }

    /**
     * Метод, который выгружает из БД фото питомца по id отчета.
     *
     * @param reportId идентификатор id отчета.
     * @return ResponseEntity с найденным фото отчета.
     */
    @ApiOperation(value = "Найти фото питомца по id отчета.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Фото питомца успешно найдено."),
            @ApiResponse(code = 404, message = "Фото питомца не найдено."),
            @ApiResponse(code = 500, message = "Ошибка при поиске фото питомца.")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/get-report-photo")
    public ResponseEntity<?> find(@RequestParam("reportId") Integer reportId) { // todo переработать на id владельца животного

        var photo = reportService.findReport(reportId);
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

//    @GetMapping(value = "/{id}/report-from-db")
//    public ResponseEntity<byte[]> downloadReport(@PathVariable Integer id) {
//        Report report = reportService.findReport2(id);
//
//        var binaryContent = report.getBinaryContent();
//        var fileSystemResource = reportService.getFileSystemResource(binaryContent);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        headers.setContentLength(report.getBinaryContent().);
//
//        return ResponseEntity.status(HttpStatus.OK).
//                headers(headers).
//                body(binaryContent);
//    }

    /**
     * Метод, который получает список всех отчетов о животных постранично.
     *
     * @param pageNumber количество страниц.
     * @param pageSize размер страницы.
     * @return ResponseEntity с коллекцией объектов Report.
     */
    @ApiOperation(value = "Получить список всех отчетов о питомцах.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список отчетов о питомцах успешно получен."),
            @ApiResponse(code = 500, message = "Ошибка при получении списка отчетов о питомцах.")
    })
    @GetMapping("/all-reports-by-pages")
    public ResponseEntity<List<Report>> getReportsByPages(@RequestParam("page") Integer pageNumber,
                                                         @RequestParam("size") Integer pageSize) {
        return ResponseEntity.ok(reportService.getAllReportsByPages(pageNumber, pageSize));
    }

    @GetMapping("/all-reports-by-userId") // todo требует доработки
    public ResponseEntity<List<ReportDTO>> getAllByUserId(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(reportService.getAllReportsByUserId(userId));
    }
}
