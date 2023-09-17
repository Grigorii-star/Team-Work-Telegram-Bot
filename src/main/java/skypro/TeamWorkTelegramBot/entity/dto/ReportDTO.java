package skypro.TeamWorkTelegramBot.entity.dto;

import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Shelter;

import java.time.LocalDate;

public interface ReportDTO {
    LocalDate getDate();
    String getReport();
    Shelter getShelter();
    AnimalOwner getAnimalOwner();
}
