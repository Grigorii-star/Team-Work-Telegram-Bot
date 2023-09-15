package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.Animal;

public interface AnimalsRepository extends JpaRepository<Animal, Integer> {
}
