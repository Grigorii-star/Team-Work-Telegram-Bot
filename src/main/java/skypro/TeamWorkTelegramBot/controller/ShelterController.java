package skypro.TeamWorkTelegramBot.controller;

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

    @PostMapping
    public ResponseEntity<Shelter> add(@RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.addShelter(shelter));
    }
    @GetMapping("{id}")
    public ResponseEntity<Shelter> find(@PathVariable Long id) {
        return ResponseEntity.ok(shelterService.findShelter(id));

    }
    @PutMapping
    public ResponseEntity<Shelter> edit(@RequestBody Shelter shelter) {
        return ResponseEntity.ok(shelterService.editShelter(shelter));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        shelterService.removeShelter(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Shelter>> getAll() {
        return ResponseEntity.ok(shelterService.getAllShelters());
    }
}
