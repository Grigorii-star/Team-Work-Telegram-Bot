package skypro.TeamWorkTelegramBot.service.rest;

import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

import java.util.Collection;

public interface AnimalOwnerService {
    /**
     * Метод для поиска волонтеров из БД.
     * @return список AnimalOwner с Boolean значением isVolunteer = true из БД.
     */
    Collection<AnimalOwner> getAllVolunteers();
}
