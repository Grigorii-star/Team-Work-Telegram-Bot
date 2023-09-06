package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

public interface AnimalOwnerRepository extends JpaRepository<AnimalOwner, Integer> {
}
