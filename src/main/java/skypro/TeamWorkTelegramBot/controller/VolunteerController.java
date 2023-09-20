package skypro.TeamWorkTelegramBot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.service.rest.VolunteerService;

import java.util.Collection;

/**
 * REST API для запросов к БД с волонтерами.
 */
@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * Метод, который добавляет нового волонтера.
     *
     * @param volunteer объект Volunteer, который будет добавлен.
     * @return ResponseEntity с добавленным объектом Volunteer.
     */
    @ApiOperation(value = "Добавить нового волонтера.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Волонтер успешно добавлен."),
            @ApiResponse(code = 400, message = "Предоставлены некорректные данные."),
            @ApiResponse(code = 500, message = "Ошибка при добавлении волонтера.")
    })
    @PostMapping
    public ResponseEntity<Volunteer> add(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.addVolunteer(volunteer));
    }

    /**
     * Метод, который находит волонтера по его идентификатору.
     *
     * @param id идентификатор волонтера.
     * @return ResponseEntity с найденным объектом волонтера.
     */
    @ApiOperation(value = "Найти волонтера по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Волонтер успешно найден."),
            @ApiResponse(code = 404, message = "Волонтер не найден."),
            @ApiResponse(code = 500, message = "Ошибка при поиске волонтера.")
    })
    @GetMapping("{id}")
    public ResponseEntity<Volunteer> find(@PathVariable Integer id) {
        return ResponseEntity.ok(volunteerService.findVolunteer(id));
    }

    /**
     * Метод, который редактирует информацию о волонтере.
     *
     * @param volunteer объект Volunteer, содержащий измененную информацию о волонтере.
     * @return ResponseEntity с объектом Volunteer после редактирования.
     */
    @ApiOperation(value = "Редактировать волонтера.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о волонтере успешно отредактирована."),
            @ApiResponse(code = 400, message = "Предоставлены некорректные данные для редактирования."),
            @ApiResponse(code = 500, message = "Ошибка при редактировании информации о волонтере.")
    })
    @PutMapping
    public ResponseEntity<Volunteer> edit(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.editVolunteer(volunteer));
    }

    /**
     * Метод, который удаляет волонтера по его идентификатору.
     *
     * @param id идентификатор волонтера, который будет удален.
     * @return ResponseEntity без тела, указывающий на успешное удаление волонтера.
     */
    @ApiOperation(value = "Удалить волонтера по ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Волонтер успешно удален."),
            @ApiResponse(code = 404, message = "Волонтер не найден."),
            @ApiResponse(code = 500, message = "Ошибка при удалении волонтера.")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        volunteerService.removeVolunteer(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод, который получает список всех волонтеров.
     *
     * @return ResponseEntity с коллекцией объектов Volunteer.
     */
    @ApiOperation(value = "Получить список всех волонтеров.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список волонтеров успешно получен."),
            @ApiResponse(code = 500, message = "Ошибка при получении списка волонтеров.")
    })
    @GetMapping
    public ResponseEntity<Collection<Volunteer>> getAll() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }
}
