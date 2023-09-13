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
public class Cat implements Command {
    private final SendMessageService sendMessageService;
    public final static String GREETING_MESSAGE = "Привет! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
            "Я отвечу на все вопросы и помогу определиться с выбором.";

    String[] buttonsText = {
            "Узнать подробнее о приюте",
            "Узнать расписание работы, адрес и схему проезда",
            "Получить телефон охраны для оформления пропуска на машину",
            "Получить общие рекомендации по технике безопасности на территории приюта",
            "Оставить контактные данные, чтобы волонтер мог связаться",
            "Позвать волонтера",
            "Перейти в главное меню"};
    String[] buttonsCallData = {
            "о_приюте",
            "расписание",
            "охрана",
            "техника_безопасности",
            "контакт",
            "волонтер",
            "меню"};

    public Cat(SendMessageService sendMessageService) {
        this.sendMessageService = sendMessageService;
    }

    /**
     * Метод, который нужен для формирования ответа пользователю.
     * Содержит id пользователя и сообщение для пользователя,
     * отправляет сообщение, полученное из текстового файла,
     * и необходимые кнопки для пользователя
     *
     * @param update - id пользователя
     */
    @Override
    public void execute(Update update) {
        Long chatId = update.getCallbackQuery().getFrom().getId();//.getMessage().getChatId();
        String callData = update.getCallbackQuery().getData();
        switch (callData) {
            case "инфо": {
                //переход в stage1
                break;
            }
            case "взять_животное": {
                // переход в stage2
                //sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/cat-shelter-address.txt"), buttonsText, buttonsCallData);
                break;
            }
            case "отчет": {
                //переход в stage3
                //sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/cat-shelter-address.txt"), buttonsText, buttonsCallData);
                break;
            }
            case "волонтер": {
                // метод, который вызывает волонтёра в чат
                //sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/cat-shelter-address.txt"), buttonsText, buttonsCallData);
                break;
            }
            case "назад": {
                sendMessageService.SendMessageToUser(String.valueOf(chatId), GREETING_MESSAGE, buttonsText, buttonsCallData);
                break;
            }
        }
    }

//    @Override
//    public void execute(Update update) {
//        Long chatId = update.getCallbackQuery().getFrom().getId();//.getMessage().getChatId();
//        String callData = update.getCallbackQuery().getData();
//        switch (callData) {
//            case "о_приюте": {
//                sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/cat-shelter-info.txt"), buttonsText, buttonsCallData);
//                break;
//            }
//            case "расписание": {
//                sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/cat-shelter-address.txt"), buttonsText, buttonsCallData);
//                break;
//            }
//            case "охрана": {
//                sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/cat-security-phone.txt"), buttonsText, buttonsCallData);
//                break;
//            }
//            case "техника_безопасности": {
//                sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/safety_rules.txt"), buttonsText, buttonsCallData);
//                break;
//            }
//            case "контакт": {
//                //сохранение телефона от пользователя в БД
//                //sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/safety_rules.txt"), buttonsText, buttonsCallData);
//                break;
//            }
//            case "волонтер": {
//                //вызов волонтёра для пользователя
//                //sendMessageService.SendMessageToUser(String.valueOf(chatId), getInfo("src/main/resources/bot-files/stage1/safety.txt"), buttonsText, buttonsCallData);
//                break;
//            }
//            case "меню": {
//                sendMessageService.SendMessageToUser(String.valueOf(chatId), GREETING_MESSAGE, buttonsText, buttonsCallData);
//                break;
//            }
//        }
//        //sendMessageService.SendMessageToUser(String.valueOf(chatId), GREETING_MESSAGE);
//    }

    /**
     * Метод, который нужен для обработки текстовых файлов в String
     *
     * @return String message
     */
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
