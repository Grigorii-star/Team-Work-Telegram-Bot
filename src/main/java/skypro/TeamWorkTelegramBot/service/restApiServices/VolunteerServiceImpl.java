package skypro.TeamWorkTelegramBot.service.restApiServices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * Класс реализации интерфейса VolunteerService
 */

@Slf4j
@Service
public class VolunteerServiceImpl implements VolunteerService{
    private final VolunteersRepository volunteersRepository;

    public VolunteerServiceImpl(VolunteersRepository volunteersRepository) {
        this.volunteersRepository = volunteersRepository;
    }

    @Override
    public Volunteer addVolunteer(Volunteer volunteer) {
        log.info("Invoked a method for adding a volunteer");

        Volunteer volunteerExistsCheck = volunteersRepository.findByIdChat(volunteer.getIdChat());
        if (volunteerExistsCheck != null && volunteerExistsCheck.getIdChat().equals(volunteer.getIdChat())) {
            log.error("The volunteer {} already exists", volunteerExistsCheck);
        }

        Volunteer addedVolunteer = volunteersRepository.save(volunteer);
        log.debug("Volunteer {} was added", addedVolunteer);
        return addedVolunteer;
    }

    @Override
    public Volunteer findVolunteer(Integer id) {
        log.info("Invoked a method for finding volunteer");

        volunteerIdValidation(id);

        Volunteer foundVolunteer = volunteersRepository.findById(id).get();
        log.debug("Volunteer with id {} was found", id);
        return foundVolunteer;
    }

    @Override
    public Volunteer editVolunteer(Volunteer volunteer) {
        log.info("Invoked a method for updating a volunteer");

        volunteerIdValidation(volunteer.getId());

        Volunteer updatedVolunteer = volunteersRepository.save(volunteer);
        log.debug("Volunteer {} was updated", updatedVolunteer);
        return updatedVolunteer;
    }

    @Override
    public void removeVolunteer(Integer id) {
        log.info("Invoked a method for removing a volunteer");

        volunteerIdValidation(id);

        log.debug("Volunteer with id {} was removed", id);
        volunteersRepository.deleteById(id);
    }

    @Override
    public Collection<Volunteer> getAllVolunteers() {
        log.info("Invoked a method for getting all volunteers");

        log.debug("All volunteers were received");
        return Collections.unmodifiableCollection(volunteersRepository.findAll());
    }

    private void volunteerIdValidation(Integer id) {
        if (!volunteersRepository.existsById(id)) {
            log.error("There is no volunteer with id = {}", id);
        }
    }
}
