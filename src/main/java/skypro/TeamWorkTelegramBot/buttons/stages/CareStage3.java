package skypro.TeamWorkTelegramBot.buttons.stages;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Component
public class CareStage3 {
    public String makeAChoiceOfStage3() {
        return getInfo("src/main/resources/bot-files/stage3/menu3.txt");
    }

    private String animalPhoto() {
        return "";
    }

    private String animalDiet() {
        return "";
    }

    private String generalWellBeingAndGettingUsedToANewPlace() {
        return "";
    }

    private String changeInBehavior() {
        return "";
    }

    private String submitADailyReportForm() {
        return "";
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
