package skypro.TeamWorkTelegramBot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.service.ShelterService;

import java.util.Collection;

/**
 * Контроллер для приюта
 */
@RestController
@RequestMapping("animal-shelter")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }
    /**
     * метод, который добавляет новый приют.
     * @param shelter объект Shelter, который будет добавлен
     * @return ResponseEntity с добавленным объектом Shelter
     */
    @ApiOperation(value = "Добавить новый приют")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Приют успешно добавлен"),
            @ApiResponse(code = 400, message = "Предоставлены некорректные данные"),
            @ApiResponse(code = 500, message = "Ошибка при добавлении приюта")
    })
    @PostMapping
    public ResponseEntity<Shelter> add(@RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.addShelter(shelter));
    }
    /**
     * метод, который находит приют по его идентификатору.
     * @param id идентификатор приюта
     * @return ResponseEntity с найденным объектом приюта
     */
    @ApiOperation(value = "Найти приют по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Приют успешно найден"),
            @ApiResponse(code = 404, message = "Приют не найден"),
            @ApiResponse(code = 500, message = "Ошибка при поиске приюта")
    })
    @GetMapping("{id}")
    public ResponseEntity<Shelter> find(@PathVariable Integer id) {
        return ResponseEntity.ok(shelterService.findShelter(id));
    }
    /**
     * метод, который редактирует информацию о приюте.
     * @param shelter объект Shelter, содержащий измененную информацию о приюте
     * @return ResponseEntity с объектом Shelter после редактирования
     */
    @ApiOperation(value = "Редактировать приют")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о приюте успешно отредактирована"),
            @ApiResponse(code = 400, message = "Предоставлены некорректные данные для редактирования"),
            @ApiResponse(code = 500, message = "Ошибка при редактировании информации о приюте")
    })
    @PutMapping
    public ResponseEntity<Shelter> edit(@RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.editShelter(shelter));
    }
    /**
     * метод, который удаляет приют по его идентификатору.
     * @param id идентификатор приюта, который будет удален
     * @return ResponseEntity без тела, указывающий на успешное удаление приюта
     */
    @ApiOperation(value = "Удалить приют по ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Приют успешно удален"),
            @ApiResponse(code = 404, message = "Приют не найден"),
            @ApiResponse(code = 500, message = "Ошибка при удалении приюта")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        shelterService.removeShelter(id);
        return ResponseEntity.ok().build();
    }
    /**
     * метод, который получает список всех приютов.
     * @return ResponseEntity с коллекцией объектов Shelter
     */
    @ApiOperation(value = "Получить список всех приютов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список приютов успешно получен"),
            @ApiResponse(code = 500, message = "Ошибка при получении списка приютов")
    })
    @GetMapping
    public ResponseEntity<Collection<Shelter>> getAll() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }
}
