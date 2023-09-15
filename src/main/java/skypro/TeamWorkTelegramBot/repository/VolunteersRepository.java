package skypro.TeamWorkTelegramBot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.Volunteer;

import java.util.Optional;

public interface VolunteersRepository extends JpaRepository<Volunteer, Integer> {
    Volunteer findByIdChat(Long idChat);
}
