package skypro.TeamWorkTelegramBot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.service.restApiServices.ReportService;

import java.util.Collection;

/**
 * контроллер для животного
 */
@RestController
@RequestMapping("animal-report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * метод, который находит отчет о животном по идентификатору владельца.
     * @param id идентификатор владельца животного
     * @return ResponseEntity с найденным объектом отчета
     */
    @ApiOperation(value = "Найти отчет о животном по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отчет о животном успешно найдено"),
            @ApiResponse(code = 404, message = "Отчет о животном не найдено"),
            @ApiResponse(code = 500, message = "Ошибка при поиске отчета о животном")
    })
    @GetMapping("{id}")
    public ResponseEntity<Report> find(@PathVariable Integer id) {
        return ResponseEntity.ok(reportService.findReport(id));
    }

    /**
     * метод, который получает список всех отчетов о животных.
     * @return ResponseEntity с коллекцией объектов Report
     */
    @ApiOperation(value = "Получить список всех отчетов о животных")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список отчетов о животных успешно получен"),
            @ApiResponse(code = 500, message = "Ошибка при получении списка отчетов о животных")
    })
    @GetMapping
    public ResponseEntity<Collection<Report>> getAll() {
        return ResponseEntity.ok(reportService.getAllReports());
    }
}
