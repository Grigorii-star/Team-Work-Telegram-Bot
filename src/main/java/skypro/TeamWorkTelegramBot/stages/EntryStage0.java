package skypro.TeamWorkTelegramBot.stages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class EntryStage0 {

    private final AnimalOwnerRepository animalOwnerRepository;
    private final StageSelector stageSelector;
    private final ConsultationStage1 consultationStage1;
    private final StageOfPreparationOfDocuments2 stageOfPreparationOfDocuments2;
    private final CareStage3 careStage3;

    @Autowired
    public EntryStage0(AnimalOwnerRepository animalOwnerRepository,
                       StageSelector stageSelector,
                       ConsultationStage1 consultationStage1,
                       StageOfPreparationOfDocuments2 stageOfPreparationOfDocuments2,
                       CareStage3 careStage3) {
        this.animalOwnerRepository = animalOwnerRepository;
        this.stageSelector = stageSelector;
        this.consultationStage1 = consultationStage1;
        this.stageOfPreparationOfDocuments2 = stageOfPreparationOfDocuments2;
        this.careStage3 = careStage3;
    }

    public SendMessage greetingNewOwnerMessage(Long chatId, String userName) {
        return new SendMessage(String.valueOf(chatId), "Привет " + userName + "! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
                "Я отвечу на все вопросы и помогу определиться с выбором.");
    }

    public SendMessage aboutShelterMessage(Long chatId) {
        return new SendMessage(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"));
    }

    public SendMessage animalChoice(Long chatId) {
        return new SendMessage(String.valueOf(chatId), "Напиши 1, если ты хочешь завести собаку. " +
                "\n\nНапиши 2, если ты хочешь завести кошку. ");
    }

    public SendMessage makeAChoiceOfStage0(Long chatId) {
        return new SendMessage(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage0/menu0.txt"));
    }

    public String getInformationAboutTheShelterGreeting() { //аналогичный метод в ConsultationStage: talkAboutShelter()
        return consultationStage1.userGreeting(); //возможно тут делать переадресацию на этап(1) ConsultationStage
    }

    public String getInformationAboutTheShelterChooseOfStage() {
        return consultationStage1.makeAChoiceOfStage1();
    }

    public String informationAboutTakingAnAnimalShelterGreeting() {
        return stageOfPreparationOfDocuments2.userGreeting(); //возможно тут делать переадресацию на этап(2) StageOfPreparationOfDocuments
    }

    public String informationAboutTakingAnAnimalShelterChooseOfStage(Long chatId) {
        return stageOfPreparationOfDocuments2.makeAChoiceOfStage2(chatId);
    }

    public String submitAPetReport() {
        return careStage3.makeAChoiceOfStage3(); //возможно тут делать переадресацию на этап(3) CareStage
    }

    public String callAVolunteer() { //этот метот присутствует на всех этапах
        return "Вызываю волонтера.";
    }

    private String getInfo(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}

