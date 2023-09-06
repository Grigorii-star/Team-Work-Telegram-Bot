package skypro.TeamWorkTelegramBot.stages;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.util.Optional;

@Service
public class StageSelector {

    private final Integer STAGE_0 = 0;
    private final Integer STAGE_1 = 1;
    private final Integer STAGE_2 = 2;
    private final Integer STAGE_3 = 3;

    private final AnimalOwnerRepository animalOwnerRepository;

    public StageSelector(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }


    public int ifFirstTime(Long chatId) {
        Optional<AnimalOwner> checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);

        if (checkAnimalOwner.isEmpty()) {
            AnimalOwner animalOwner = new AnimalOwner();
            animalOwner.setIdChat(chatId);
            animalOwner.setStage(STAGE_0);
            animalOwnerRepository.save(animalOwner);
            return -1;
        }
        return 0;
    }
}
