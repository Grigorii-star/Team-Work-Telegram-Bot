package skypro.TeamWorkTelegramBot.service;

import skypro.TeamWorkTelegramBot.entity.Volunteer;

import java.util.Collection;

public interface VolunteerService {
    Volunteer addVolunteer(Volunteer volunteer);
    Volunteer findVolunteer(Long id);
    Volunteer editVolunteer(Volunteer volunteer);
    void removeVolunteer(Long id);
    Collection<Volunteer> getAllVolunteers();
}
