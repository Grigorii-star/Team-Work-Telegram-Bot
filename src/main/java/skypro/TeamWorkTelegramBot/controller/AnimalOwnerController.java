package skypro.TeamWorkTelegramBot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.service.rest.AnimalOwnerService;

import java.util.Collection;

@RestController
@RequestMapping("animal-owner")
public class AnimalOwnerController {
    private final AnimalOwnerService animalOwnerService;
    public AnimalOwnerController(AnimalOwnerService animalOwnerService) {
        this.animalOwnerService = animalOwnerService;
    }

    /**
     * Метод, который получает список всех волонтеров.
     * @return ResponseEntity с коллекцией объектов AnimalOwner с Boolean значением isVolunteer.
     */
    @Operation(
            summary = "ПОЛУЧИТЬ СПИСОК ВСЕХ ЖЕЛАЮЩИХ СТАТЬ ВОЛОНТЕРАМИ",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Список волонтеров успешно получен",
                            content = @Content(
                                    schema = @Schema(implementation = AnimalOwner.class),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "404",
                            description = "волонтеры не найдены",
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
            tags = "Animal owner controller"
    )
    @GetMapping
    public ResponseEntity<Collection<AnimalOwner>> getAllVolunteers() {
        return ResponseEntity.ok(animalOwnerService.getAllVolunteers());
    }
}
