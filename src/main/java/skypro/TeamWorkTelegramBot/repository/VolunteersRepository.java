package skypro.TeamWorkTelegramBot.repository;

import org.apache.coyote.http11.filters.VoidOutputFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface VolunteersRepository extends JpaRepository<Volunteer, Integer> {
    Volunteer findByIdChat(Long idChat);

    Volunteer findDistinctFirstByIsBusy(Boolean isBusy);

    Volunteer findByAnimalOwner(AnimalOwner animalOwner);

    List<Volunteer> findVolunteersByIsBusy(Boolean isBusy);

}
