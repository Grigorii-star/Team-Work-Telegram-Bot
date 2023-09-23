package skypro.TeamWorkTelegramBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.service.rest.ShelterService;

import java.util.Collection;

/**
 * REST API для запросов к БД с приютами для питомцев.
 */
@RestController
@RequestMapping("animal-shelter")
public class ShelterController {
    private final ShelterService shelterService;
    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * Метод, который добавляет новый приют.
     *
     * @param shelter объект Shelter, который будет добавлен.
     * @return ResponseEntity с добавленным объектом Shelter.
     */
    @Operation(
            summary = "ДОБАВИТЬ НОВЫЙ ПРИЮТ ДЛЯ ЖИВОТНЫХ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Внести новый приют для животных",
                    content = @Content(
                            schema = @Schema(implementation = Shelter.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "id: Вносить не требуется. Назначается автоматически"
                                    ),
                                    @ExampleObject(
                                            name = "name: Домик"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Приют успешно добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Предоставлены некорректные данные",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при добавлении приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Shelter controller"
    )
    @PostMapping
    public ResponseEntity<Shelter> add(@RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.addShelter(shelter));
    }

    /**
     * Метод, который находит приют по его идентификатору.
     *
     * @param id идентификатор приюта.
     * @return ResponseEntity с найденным объектом приюта.
     */
    @Operation(
            summary = "НАЙТИ ПРИЮТ ДЛЯ ЖИВОТНЫХ ПО id ПРИЮТА",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Приют успешно найден",
                            content = @Content(
                                    schema = @Schema(implementation = Shelter.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Приют не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при поиске приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Shelter controller"
    )
    @GetMapping("{id}")
    public ResponseEntity<Shelter> find(@Parameter(description = "Номер id приюта")
                                        @PathVariable Integer id) {
        return ResponseEntity.ok(shelterService.findShelter(id));
    }

    /**
     * Метод, который редактирует информацию о приюте.
     *
     * @param shelter объект Shelter, содержащий измененную информацию о приюте.
     * @return ResponseEntity с объектом Shelter после редактирования.
     */
    @Operation(
            summary = "РЕДАКТИРОВАТЬ ПРИЮТ ДЛЯ ЖИВОТНЫХ",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отредактируй приют для животных",
                    content = @Content(
                            schema = @Schema(implementation = Shelter.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "id: Внести id приюта, для его редактирования"
                                    ),
                                    @ExampleObject(
                                            name = "name: Домик"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Информация о приюте успешно отредактирована",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Предоставлены некорректные данные для редактирования",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при редактировании информации о приюте",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Shelter controller"
    )
    @PutMapping
    public ResponseEntity<Shelter> edit(@RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.editShelter(shelter));
    }

    /**
     * Метод, который удаляет приют по его идентификатору.
     *
     * @param id идентификатор приюта, который будет удален.
     * @return ResponseEntity без тела, указывающий на успешное удаление приюта.
     */
    @Operation(
            summary = "УДАЛИТЬ ПРИЮТ ДЛЯ ЖИВОТНЫХ ПО id ПРИЮТА",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Приют успешно удален",
                            content = @Content(
                                    schema = @Schema(implementation = Shelter.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Приют не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при удалении приюта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Shelter controller"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@Parameter(description = "Номер id приюта")
                                       @PathVariable Integer id) {
        shelterService.removeShelter(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод, который получает список всех приютов.
     * @return ResponseEntity с коллекцией объектов Shelter.
     */
    @Operation(
            summary = "ПОЛУЧИТЬ СПИСОК ВСЕХ ПРИЮТОВ ДЛЯ ЖИВОТНЫХ",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Список приютов успешно получен",
                            content = @Content(
                                    schema = @Schema(implementation = Shelter.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Приюты не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при получении списка приютов",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Shelter controller"
    )
    @GetMapping
    public ResponseEntity<Collection<Shelter>> getAll() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }

    /**
     * Метод, который получает список всех владельцев питомцев приюта.
     * @return ResponseEntity с коллекцией объектов AnimalOwner.
     */
    @Operation(
            summary = "ПОЛУЧИТЬ СПИСОК ВСЕХ ВЛАДЕЛЬЦЕВ ПИТОМЦЕВ, ПО id ПРИЮТА ДЛЯ ЖИВОТНЫХ",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Список владельцев питомцев успешно получен",
                            content = @Content(
                                    schema = @Schema(implementation = AnimalOwner.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Владельцы питомцев не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при получении списка владельцев питомцев",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Shelter controller"
    )
    @GetMapping("/animal-owners-by-id/{id}")
    public ResponseEntity<Collection<AnimalOwner>> getShelterAnimalOwners(@Parameter(description = "Номер id приюта")
                                                                          @PathVariable Integer id) {
        return ResponseEntity.ok(shelterService.getShelterAnimalOwners(id));
    }
}
