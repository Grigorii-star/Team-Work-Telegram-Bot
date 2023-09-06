package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

import java.util.Optional;

public interface AnimalOwnerRepository extends JpaRepository<AnimalOwner, Integer> {
    Optional<AnimalOwner> findByIdChat(Long chatId);
}
