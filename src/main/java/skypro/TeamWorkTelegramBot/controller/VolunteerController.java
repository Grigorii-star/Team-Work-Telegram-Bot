package skypro.TeamWorkTelegramBot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.service.VolunteerService;

import java.util.Collection;

/**
 * Контроллер для волонтера
 */
@RestController
@RequestMapping("volunteer")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public ResponseEntity<Volunteer> add(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.addVolunteer(volunteer));
    }
    @GetMapping("{id}")
    public ResponseEntity<Volunteer> find(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.findVolunteer(id));

    }
    @PutMapping
    public ResponseEntity<Volunteer> edit(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.editVolunteer(volunteer));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        volunteerService.removeVolunteer(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<Collection<Volunteer>> getAll() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }
}
