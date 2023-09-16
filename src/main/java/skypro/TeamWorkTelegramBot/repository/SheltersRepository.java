package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.Shelter;
import skypro.TeamWorkTelegramBot.entity.Volunteer;

public interface SheltersRepository extends JpaRepository<Shelter, Integer> {
    Shelter findByName(String name);
}
