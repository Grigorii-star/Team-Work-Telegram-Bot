package skypro.TeamWorkTelegramBot.service.rest;

import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class AnimalOwnerServiceImpl implements AnimalOwnerService{
    private final AnimalOwnerRepository animalOwnerRepository;
    public AnimalOwnerServiceImpl(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод для поиска волонтеров из БД.
     * @return список AnimalOwner с Boolean значением isVolunteer = true из БД.
     */
    @Override
    public Collection<AnimalOwner> getAllVolunteers() {
        return Collections.unmodifiableCollection(animalOwnerRepository.findByIsVolunteer());
    }
}
