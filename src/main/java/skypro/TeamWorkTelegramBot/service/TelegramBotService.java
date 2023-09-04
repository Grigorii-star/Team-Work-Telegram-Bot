package skypro.TeamWorkTelegramBot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Service
public class TelegramBotService implements UpdatesListener {
    private final TelegramBot telegramBot;

    @Autowired
    public TelegramBotService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            Long chatId = update.message().chat().id();
            String text = update.message().text();
            String userName = update.message().from().firstName();

            if (text.equals("/start")){
                // todo здесь нужно залезть в бд, поискать пользователя, если он уже был, то мы пропускаем приветствие
                SendMessage message = new SendMessage(chatId, getGreetingText(userName));
                SendResponse response = telegramBot.execute(message);
                message = new SendMessage(chatId,getInfo());
                response = telegramBot.execute(message);
            }

            if (text.equals("1")) {

            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private String getGreetingText(String userName) {
        return "Привет " + userName + "! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
                "Я отвечу на все вопросы и помогу определиться с выбором.";

    }

    private String getInfo() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/about_shelter.txt"))) {
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

    private String getChoice() {
        return  "Напиши 1, если ты хочешь завести собаку. " +
                "\n\nНапиши 2, если ты хочешь завести кошку. ";
    }
}
