package skypro.TeamWorkTelegramBot.service;

import skypro.TeamWorkTelegramBot.entity.Shelter;

import java.util.Collection;

public interface ShelterService {
    Shelter addShelter(Shelter shelter);
    Shelter findShelter(Long id);
    Shelter editShelter(Shelter shelter);
    void removeShelter(Long id);
    Collection<Shelter> getAllShelters();
}
