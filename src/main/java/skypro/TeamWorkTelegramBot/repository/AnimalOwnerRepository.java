package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;

import java.util.Optional;

/**
 *Этот интерфейс используется для доступа к данным AnimalOwner в базе данных.
 */
public interface AnimalOwnerRepository extends JpaRepository<AnimalOwner, Integer> {
    AnimalOwner findByIdChat(Long chatId);
}
