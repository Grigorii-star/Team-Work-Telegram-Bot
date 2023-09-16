package skypro.TeamWorkTelegramBot.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Animal;
import skypro.TeamWorkTelegramBot.service.AnimalService;

import java.util.Collection;

/**
 * контроллер для животного
 */
@RestController
@RequestMapping("animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }
    /**
     * метод, который добавляет нового животного.
     * @param animal объект Animal, который будет добавлен
     * @return ResponseEntity с добавленным объектом Animal
     */
    @ApiOperation(value = "Добавить нового животного")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Животное успешно добавлено"),
            @ApiResponse(code = 400, message = "Предоставлены некорректные данные"),
            @ApiResponse(code = 500, message = "Ошибка при добавлении животного")
    })
    @PostMapping
    public ResponseEntity<Animal> add(@RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.addAnimal(animal));
    }
    /**
     * метод, который находит животное по его идентификатору.
     * @param id идентификатор животного
     * @return ResponseEntity с найденным объектом животного
     */
    @ApiOperation(value = "Найти животное по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Животное успешно найдено"),
            @ApiResponse(code = 404, message = "Животное не найдено"),
            @ApiResponse(code = 500, message = "Ошибка при поиске животного")
    })
    @GetMapping("{id}")
    public ResponseEntity<Animal> find(@PathVariable Integer id) {
        return ResponseEntity.ok(animalService.findAnimal(id));

    }
    /**
     * метод, который редактирует информацию о волонтере.
     * @param animal объект Animal, содержащий измененную информацию о животном
     * @return ResponseEntity с объектом Animal после редактирования
     */
    @ApiOperation(value = "Редактировать животное")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о животном успешно отредактирована"),
            @ApiResponse(code = 400, message = "Предоставлены некорректные данные для редактирования"),
            @ApiResponse(code = 500, message = "Ошибка при редактировании информации о животном")
    })
    @PutMapping
    public ResponseEntity<Animal> edit(@RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.editAnimal(animal));
    }
    /**
     * метод, который удаляет животоне по его идентификатору.
     * @param id идентификатор животного, который будет удален
     * @return ResponseEntity без тела, указывающий на успешное удаление животного
     */
    @ApiOperation(value = "Удалить животное по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Животное успешно удалено"),
            @ApiResponse(code = 404, message = "Животное не найдено"),
            @ApiResponse(code = 500, message = "Ошибка при удалении животного")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Integer id) {
        animalService.removeAnimal(id);
        return ResponseEntity.ok().build();
    }
    /**
     * метод, который получает список всех животных.
     * @return ResponseEntity с коллекцией объектов Animal
     */
    @ApiOperation(value = "Получить список всех животных")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список животных успешно получен"),
            @ApiResponse(code = 500, message = "Ошибка при получении списка животных")
    })
    @GetMapping
    public ResponseEntity<Collection<Animal>> getAll() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }
}
