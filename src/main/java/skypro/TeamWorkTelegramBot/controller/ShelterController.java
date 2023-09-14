package skypro.TeamWorkTelegramBot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skypro.TeamWorkTelegramBot.service.ShelterService;

@RestController
@RequestMapping("animal-shelter")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }
}
