package skypro.TeamWorkTelegramBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
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
    @Operation(
            summary = "ДОБАВИТЬ НОВОГО ВОЛОНТЕРА",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Внести нового волонтера",
                    content = @Content(
                            schema = @Schema(implementation = Volunteer.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "id: Вносить не требуется. Назначается автоматически"
                                    ),
                                    @ExampleObject(
                                            name = "idChat: 37909765"
                                    ),
                                    @ExampleObject(
                                            name = "name: Иванов Дмитрий Сергеевич"
                                    ),
                                    @ExampleObject(
                                            name = "isBusy: обязательно назначить false"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Волонтер успешно добавлен",
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
                            description = "Ошибка при добавлении волонтера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Volunteer controller"
    )
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
    @Operation(
            summary = "НАЙТИ ВОЛОНТЕРА ПО id ВОЛОНТЕРА",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Волонтер успешно найден",
                            content = @Content(
                                    schema = @Schema(implementation = Volunteer.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Волонтер не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при поиске волонтера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Volunteer controller"
    )
    @GetMapping("{id}")
    public ResponseEntity<Volunteer> find(@Parameter(description = "Номер id волонтера")
                                          @PathVariable Integer id) {
        return ResponseEntity.ok(volunteerService.findVolunteer(id));
    }

    /**
     * Метод, который редактирует информацию о волонтере.
     *
     * @param volunteer объект Volunteer, содержащий измененную информацию о волонтере.
     * @return ResponseEntity с объектом Volunteer после редактирования.
     */
    @Operation(
            summary = "РЕДАКТИРОВАТЬ ВОЛОНТЕРА",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отредактировать волонтера",
                    content = @Content(
                            schema = @Schema(implementation = Volunteer.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {
                                    @ExampleObject(
                                            name = "id: Внести id волонтера, для его редактирования"
                                    ),
                                    @ExampleObject(
                                            name = "idChat: 37909765"
                                    ),
                                    @ExampleObject(
                                            name = "name: Иванов Дмитрий Сергеевич"
                                    ),
                                    @ExampleObject(
                                            name = "isBusy: обязательно назначить false"
                                    )
                            }
                    )
            ),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Информация о волонтере успешно отредактирована",
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
                            description = "Ошибка при редактировании информации о волонтере",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Volunteer controller"
    )
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
    @Operation(
            summary = "УДАЛИТЬ ВОЛОНТЕРА ПО id ВОЛОНТЕРА",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Волонтер успешно удален",
                            content = @Content(
                                    schema = @Schema(implementation = Volunteer.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Волонтер не найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при удалении волонтера",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Volunteer controller"
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@Parameter(description = "Номер id волонтера")
                                       @PathVariable Integer id) {
        volunteerService.removeVolunteer(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Метод, который получает список всех волонтеров.
     *
     * @return ResponseEntity с коллекцией объектов Volunteer.
     */
    @Operation(
            summary = "ПОЛУЧИТЬ СПИСОК ВСЕХ ВОЛОНТЕРОВ",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Список волонтеров успешно получен",
                            content = @Content(
                                    schema = @Schema(implementation = Volunteer.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "Волонтеры не найдены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "500",
                            description = "Ошибка при получении списка волонтеров",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            },
            tags = "Volunteer controller"
    )
    @GetMapping
    public ResponseEntity<Collection<Volunteer>> getAll() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }
}
