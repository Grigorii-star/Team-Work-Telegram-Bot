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
import skypro.TeamWorkTelegramBot.service.restApiServices.ReportService;

import java.util.Collection;
import java.util.List;

/**
 * контроллер для животного
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
     * метод, который находит отчет о животном по идентификатору владельца.
     * @param reportId идентификатор владельца животного
     * @return ResponseEntity с найденным объектом отчета
     */
    @ApiOperation(value = "Найти отчет о животном по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отчет о животном успешно найдено"),
            @ApiResponse(code = 404, message = "Отчет о животном не найдено"),
            @ApiResponse(code = 500, message = "Ошибка при поиске отчета о животном")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/get-report")
    public ResponseEntity<?> find(@RequestParam("reportId") Integer reportId) {

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
     * метод, который получает список всех отчетов о животных постранично.
     * @param
     * @return ResponseEntity с коллекцией объектов Report
     */
    @ApiOperation(value = "Получить список всех отчетов о животных")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список отчетов о животных успешно получен"),
            @ApiResponse(code = 500, message = "Ошибка при получении списка отчетов о животных")
    })
    @GetMapping("/all-reports-by-pages")
    public ResponseEntity<List<Report>> getReportsByPages(@RequestParam("page") Integer pageNumber,
                                                         @RequestParam("size") Integer pageSize) {
        return ResponseEntity.ok(reportService.getAllReportsByPages(pageNumber, pageSize));
    }

    @GetMapping("/all-reports-by-userId")
    public ResponseEntity<List<ReportDTO>> getAllByUserId(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(reportService.getAllReportsByUserId(userId));
    }
}
