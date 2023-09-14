package skypro.TeamWorkTelegramBot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Animal;
import skypro.TeamWorkTelegramBot.service.AnimalService;

import java.util.Collection;

/**
 * контроллер
 */
@RestController
@RequestMapping("animal")
public class AnimalController {
    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public ResponseEntity<Animal> add(@RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.addAnimal(animal));
    }
    @GetMapping("{id}")
    public ResponseEntity<Animal> find(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.findAnimal(id));

    }
    @PutMapping
    public ResponseEntity<Animal> edit(@RequestBody Animal animal) {
        return ResponseEntity.ok(animalService.editAnimal(animal));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        animalService.removeAnimal(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Animal>> getAll() {
        return ResponseEntity.ok(animalService.getAllAnimals());
    }
}
