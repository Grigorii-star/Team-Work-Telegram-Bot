package skypro.TeamWorkTelegramBot.stages;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.service.SendMessageService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс, который нужен для формирования ответа пользователю
 */
@Component
public class EntryStage0 implements Command {
    private final SendMessageService sendMessageService;
    public EntryStage0(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    public final static String GREETING_MESSAGE = "Привет! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
                "Я отвечу на все вопросы и помогу определиться с выбором.";

    String[] buttonsText = {"Узнать информацию о приюте",
                            "Взять животное из приюта",
                            "Прислать отчет о питомце",
                            "Позвать волонтера",
                            "Выбрать другой приют"};
    String[] buttonsCallData = {"инфо",
                                "взять_животное",
                                "отчет",
                                "волонтер",
                                "назад"};

    @Override
    public void execute(Update update) {
        Long chatId = update.getCallbackQuery().getFrom().getId();//.getMessage().getChatId();
        String callData = update.getCallbackQuery().getData();
        if (callData.equals("собака") || callData.equals("кошка")) {
        sendMessageService.SendMessageToUser(String.valueOf(chatId), GREETING_MESSAGE, buttonsText, buttonsCallData);
        }
    }

//    public SendMessage greetingNewOwnerMessage(Long chatId, String userName) {
//        return new SendMessage(String.valueOf(chatId), "Привет " + userName + "! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
//                "Я отвечу на все вопросы и помогу определиться с выбором.");
//    }
//
//    public SendMessage aboutShelterMessage(Long chatId) {
//        return new SendMessage(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"));
//    }
//
//    public SendMessage animalChoice(Long chatId) {
//        return new SendMessage(String.valueOf(chatId), "Напиши 1, если ты хочешь завести собаку. " +
//                "\n\nНапиши 2, если ты хочешь завести кошку. ");
//    }
//
//    public SendMessage makeAChoiceOfStage0(Long chatId) {
//        return new SendMessage(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage0/menu0.txt"));
//    }
//
//    public String getInformationAboutTheShelterGreeting() { //аналогичный метод в ConsultationStage: talkAboutShelter()
//        return consultationStage1.userGreeting(); //возможно тут делать переадресацию на этап(1) ConsultationStage
//    }
//
//    public String getInformationAboutTheShelterChooseOfStage() {
//        return consultationStage1.makeAChoiceOfStage1();
//    }
//
//    public String informationAboutTakingAnAnimalShelterGreeting() {
//        return stageOfPreparationOfDocuments2.userGreeting(); //возможно тут делать переадресацию на этап(2) StageOfPreparationOfDocuments
//    }
//
//    public String informationAboutTakingAnAnimalShelterChooseOfStage(Long chatId) {
//        return stageOfPreparationOfDocuments2.makeAChoiceOfStage2(chatId);
//    }
//
//    public String submitAPetReport() {
//        return careStage3.makeAChoiceOfStage3(); //возможно тут делать переадресацию на этап(3) CareStage
//    }
//
//    public String callAVolunteer() { //этот метот присутствует на всех этапах
//        return "Вызываю волонтера.";
//    }

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

