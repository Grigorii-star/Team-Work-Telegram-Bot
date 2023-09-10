package skypro.TeamWorkTelegramBot.stages;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Component
public class ConsultationStage1 {

    private final AnimalOwnerRepository animalOwnerRepository;

    @Autowired
    public ConsultationStage1(AnimalOwnerRepository animalOwnerRepository) {
        this.animalOwnerRepository = animalOwnerRepository;
    }

    public String userGreeting() { //аналогичный метод в StageOfPreparationOfDocuments
        return "Молодец, что перешёл в меню консультаций! " +
                "\nДля продолжения, пожалуйста, ознакомься с информацией о приюте.";
    }

    public String makeAChoiceOfStage1() {
        return getInfo("src/main/resources/bot-files/stage1/menu1.txt");
    }

    public String getInformationAboutTheShelter(Long chatId) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage1/dog-shelter-info.txt");
        } else {
            return getInfo("src/main/resources/bot-files/stage1/cat-shelter-info.txt");
        }
    }

    public String giveOutTheSheltersWorkScheduleAndAddressAndDirections(Long chatId) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage1/dog-schedule-address.txt");
        } else {
            return getInfo("src/main/resources/bot-files/stage1/cat-schedule-address.txt");
        }
    }

    public String provideSecurityContactInformation(Long chatId) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (animalOwner.getDogLover()) {
            return getInfo("src/main/resources/bot-files/stage1/dog-security-phone.txt");
        } else {
            return getInfo("src/main/resources/bot-files/stage1/cat-security-phone.txt");
        }
    }

    public String issueGeneralSafetyAdvice() {
        return getInfo("src/main/resources/bot-files/stage1/safety_rules.txt");
    }

    public String acceptAndRecordContactDetails(Long chatId) { //аналогичный метод в StageOfPreparationOfDocuments
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
