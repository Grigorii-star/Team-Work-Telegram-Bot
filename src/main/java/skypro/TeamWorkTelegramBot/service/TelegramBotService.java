package skypro.TeamWorkTelegramBot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class TelegramBotService implements UpdatesListener {
    private final Integer STAGE_0 = 0;
    private final Integer STAGE_1 = 1;
    private final Integer STAGE_2 = 2;
    private final Integer STAGE_3 = 3;
    @Autowired
    private AnimalOwnerRepository animalOwnerRepository;
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
                //получили сообщение
                //вытащили из него чат айди и залезли в бд
                //смотрим этап пользователя, если null, то присваиваем этап 0, сохраняем и отыгрываем по нему
                // пользвоатель выбрал собаку,. присваивается этап 1
                // todo здесь нужно залезть в бд, поискать пользователя, если он уже был, то мы пропускаем приветствие

                AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

                if (animalOwner.getIdChat() == null) {
                    animalOwner.setIdChat(chatId);
                    animalOwner.setStage(STAGE_0);
                    animalOwnerRepository.save(animalOwner);

                    SendMessage greetingMessage = new SendMessage(chatId, getGreetingText(userName));
                    telegramBot.execute(greetingMessage);

                    SendMessage greetingMessage2 = new SendMessage(chatId,getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"));
                    telegramBot.execute(greetingMessage2);

                    animalChoice(chatId, getChoice());
                }
                else if (animalOwner.getStage().equals(STAGE_0)) {
                    animalChoice(chatId, getChoice());
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void animalChoice(Long chatId, String choice) {
        SendMessage choiceMessage = new SendMessage(chatId, choice);
        telegramBot.execute(choiceMessage);
    }

    private String getGreetingText(String userName) {
        return "Привет " + userName + "! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
                "Я отвечу на все вопросы и помогу определиться с выбором.";

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

    private String getChoice() {
        return  "Напиши 1, если ты хочешь завести собаку. " +
                "\n\nНапиши 2, если ты хочешь завести кошку. ";
    }
}
