package skypro.TeamWorkTelegramBot.stages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class ConsultationStage1 {

    private final AnimalOwnerRepository animalOwnerRepository;

    @Autowired
    public ConsultationStage1(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }


    private String userGreeting() { //аналогичный метод в StageOfPreparationOfDocuments
        return "Молодец, что перешёл в меню консультаций!\n" +
                "Для продолжения, пожалуйста, ознакомься с информацией о приюте.";
    }

    public String talkAboutShelter() { //аналогичный метод в EntryStage: getInformationAboutTheShelter()
        return getInfo("src/main/resources/bot-files/stage0/about_shelter.txt");
    }

    private String giveOutTheSheltersWorkScheduleAndAddressAndDirections(Long chatId) {
        Optional<AnimalOwner> animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.isEmpty()) {
            throw new RuntimeException("В базе нет информации, какой питомник выбрал пользователь");
        }
        AnimalOwner checkedAnimalOwner = animalOwner.get();
        if (checkedAnimalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage1/dog-schedule-address.txt");
        } else {
            getInfo("src/main/resources/bot-files/stage1/cat-schedule-address.txt");
        }
        return "информация не найдена";
    }

    private String provideSecurityContactInformation(Long chatId) {
        Optional<AnimalOwner> animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.isEmpty()) {
            throw new RuntimeException("В базе нет информации, какой питомник выбрал пользователь");
        }
        AnimalOwner checkedAnimalOwner = animalOwner.get();
        if (checkedAnimalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage1/dog-security-phone.txt");
        } else {
            getInfo("src/main/resources/bot-files/stage1/cat-security-phone.txt");
        }
        return "информация не найдена";
    }

    private String issueGeneralSafetyAdvice() {
        return getInfo("src/main/resources/bot-files/stage1/safety_rules.txt");
    }

    private String acceptAndRecordContactDetails() { //аналогичный метод в StageOfPreparationOfDocuments
        return "ц";
    }

    private String callAVolunteer() { //этот метот присутствует на всех этапах
        return "";
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
