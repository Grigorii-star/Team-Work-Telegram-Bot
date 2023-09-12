package skypro.TeamWorkTelegramBot.stages;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

@Component
public class StageSelector {
    private final Integer STAGE_START = -1;
    private final Integer STAGE_0 = 0;
    private final Integer STAGE_1 = 1;
    private final Integer STAGE_2 = 2;
    private final Integer STAGE_3 = 3;

    private final AnimalOwnerRepository animalOwnerRepository;

    public StageSelector(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }

    public void ifFirstTime(Long chatId) {
        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (checkAnimalOwner == null) {
            AnimalOwner animalOwner = new AnimalOwner();
            animalOwner.setIdChat(chatId);
            animalOwner.setStage(STAGE_START);
            animalOwnerRepository.save(animalOwner);
        }
    }

    public void switchToStage0(Long chatId) {
        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);
            checkAnimalOwner.setStage(STAGE_0);
            animalOwnerRepository.save(checkAnimalOwner);
    }

    public void switchToStage1(Long chatId) {
        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);
            checkAnimalOwner.setStage(STAGE_1);
            animalOwnerRepository.save(checkAnimalOwner);
    }

    public void switchToStage2(Long chatId) {
        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);
            checkAnimalOwner.setStage(STAGE_2);
            animalOwnerRepository.save(checkAnimalOwner);
    }

    public void switchToStage3(Long chatId) {
        AnimalOwner checkAnimalOwner = animalOwnerRepository.findByIdChat(chatId);
            checkAnimalOwner.setStage(STAGE_3);
            animalOwnerRepository.save(checkAnimalOwner);
    }
}
