package skypro.TeamWorkTelegramBot.stages;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

@Component
public class EntryStage0 {

    private final AnimalOwnerRepository animalOwnerRepository;

    @Autowired
    public EntryStage0(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }

    public SendMessage animalChoice(Long chatId) {
        SendMessage choiceMessage = new SendMessage(chatId, getChoice());
        return choiceMessage;
    }

    private String getChoice() {
        return  "Напиши 1, если ты хочешь завести собаку. " +
                "\n\nНапиши 2, если ты хочешь завести кошку. ";
    }


    private String chooseAShelter() {
        return "";
    }

    private String getInformationAboutTheShelter() { //аналогичный метод в ConsultationStage: talkAboutShelter()
        return ""; //возможно тут делать переадресацию на этап(1) ConsultationStage
    }

    private String informationAboutTakingAnAnimal() {
        return ""; //возможно тут делать переадресацию на этап(2) StageOfPreparationOfDocuments
    }

    private String submitAPetReport() {
        return ""; //возможно тут делать переадресацию на этап(3) CareStage
    }

    private String callAVolunteer() { //этот метот присутствует на всех этапах
        return "";
    }
}
