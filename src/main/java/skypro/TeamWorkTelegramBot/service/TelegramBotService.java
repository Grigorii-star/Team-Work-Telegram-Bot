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
import skypro.TeamWorkTelegramBot.stages.EntryStage0;
import skypro.TeamWorkTelegramBot.stages.StageSelector;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
public class TelegramBotService implements UpdatesListener {


    private final AnimalOwnerRepository animalOwnerRepository;
    private final TelegramBot telegramBot;
    private final EntryStage0 entryStage0;

    private final StageSelector stageSelector;

    @Autowired
    public TelegramBotService(TelegramBot telegramBot,
                              AnimalOwnerRepository animalOwnerRepository,
                              EntryStage0 entryStage0,
                              StageSelector stageSelector) {
        this.telegramBot = telegramBot;
        this.animalOwnerRepository = animalOwnerRepository;
        this.entryStage0 = entryStage0;
        this.stageSelector = stageSelector;
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

                int currentStage = stageSelector.ifFirstTime(chatId);

                if (currentStage == -1) {
                    SendMessage greetingMessage = new SendMessage(chatId, getGreetingText(userName));
                    telegramBot.execute(greetingMessage);
                    SendMessage greetingMessage2 = new SendMessage(chatId,getInfo("src/main/resources/bot-files/stage0/about_shelter.txt"));
                    telegramBot.execute(greetingMessage2);
                    telegramBot.execute(entryStage0.animalChoice(chatId));
                }

                if (currentStage == 0) {
                    telegramBot.execute(entryStage0.animalChoice(chatId));
                }

                if (currentStage == 1) {

                }

                if (currentStage == 2) {

                }

                if (currentStage == 3) {

                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
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

}
