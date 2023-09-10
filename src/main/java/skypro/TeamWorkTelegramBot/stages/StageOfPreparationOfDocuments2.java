package skypro.TeamWorkTelegramBot.stages;

import org.springframework.stereotype.Component;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class StageOfPreparationOfDocuments2 {

    private final AnimalOwnerRepository animalOwnerRepository;

    public StageOfPreparationOfDocuments2(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }

    public String userGreeting() { //аналогичный метод в ConsultationStage
        return "Я рад, что ты готов к встрече с новым членом семьи!\n" +
                "Для продолжения, пожалуйста, ознакомься со следующей документацией.";
    }

    public String makeAChoiceOfStage2(Long chatId) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage2/menu2dog.txt");
        } else {
            return getInfo("src/main/resources/bot-files/stage2/menu2cat.txt");
        }
    }

    public String issueRules(Long chatId) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage2/dog-rules.txt");
        } else {
            return getInfo("src/main/resources/bot-files/stage2/cat-rules.txt");
        }
    }

    public String issueAListOfDocumentsInOrderToTakeTheAnimal() {
        return getInfo("src/main/resources/bot-files/stage2/doc-list.txt");
    }

    public String issueAListOfRecommendationsForTransportation() {
        return getInfo("src/main/resources/bot-files/stage2/transfer.txt");
    }

    public String issueAListOfRecommendationsForHomeImprovementForAPuppyOrKitten(Long chatId) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage2/doggy-house.txt");
        } else {
            return getInfo("src/main/resources/bot-files/stage2/kitten-house.txt");
        }
    }

    public String issueAListOfRecommendationsForHomeImprovementForAnAdultAnimal() {
        return getInfo("src/main/resources/bot-files/stage2/adult-pet-house.txt");
    }

    public String issueAListOfRecommendationsForHomeImprovementForAnAnimalWithDisabilities() {
        return getInfo("src/main/resources/bot-files/stage2/invalid-adult-pet-house.txt");
    }

    public String giveCynologistAdviceOnInitialCommunicationWithADog() {
        return "Здесь должны быть советы кинолога по первичному общению с собакой";
    }

    public String issueRecommendationsOnProvenCynologistsForFurtherReferralToThem() {
        return "Здесь должны быть контактные данные проверенных кинологов";
    }

    public String issueAListOfReasonsForRefusal() {
        return getInfo("src/main/resources/bot-files/stage2/refuse-reasons.txt");
    }

    public String acceptAndRecordContactDetails() { //аналогичный метод в ConsultationStage
        return "Сохранить контактные данные";
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
