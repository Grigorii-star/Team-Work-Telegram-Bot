package skypro.TeamWorkTelegramBot.buttons;

import org.springframework.stereotype.Component;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.stages.Cat;
import skypro.TeamWorkTelegramBot.stages.EntryStage0;
import skypro.TeamWorkTelegramBot.stages.Stage1;
import skypro.TeamWorkTelegramBot.stages.Start;

import java.util.HashMap;
import java.util.Map;

import static skypro.TeamWorkTelegramBot.buttons.Keyboards.*;

/**
 * Класс, где хранится меню комнад в боте
 */
@Component
public class CommandKeysStorage {
    private final SendMessageService sendMessageService;
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandKeysStorage (SendMessageService sendMessageService1) {
        this.sendMessageService = sendMessageService1;
        commandMap.put(START.getCommand(), new Start(sendMessageService));

        commandMap.put(DOG.getCommand(), new EntryStage0(sendMessageService));
        commandMap.put(CAT.getCommand(), new EntryStage0(sendMessageService));

        commandMap.put(INFO.getCommand(), new Stage1(sendMessageService));
        commandMap.put(ADOPT_AN_ANIMAL.getCommand(), new Stage1(sendMessageService));
        commandMap.put(REPORT.getCommand(), new Stage1(sendMessageService));
        commandMap.put(VOLUNTEER.getCommand(), new Stage1(sendMessageService));
        commandMap.put(BACK.getCommand(), new Stage1(sendMessageService));

        commandMap.put(ABOUT_SHELTER.getCommand(), new Cat(sendMessageService));
        commandMap.put(SCHEDULE.getCommand(), new Cat(sendMessageService));
        commandMap.put(SECURITY.getCommand(), new Cat(sendMessageService));
        commandMap.put(SAFETY.getCommand(), new Cat(sendMessageService));
        commandMap.put(CONTACT.getCommand(), new Cat(sendMessageService));
        commandMap.put(MENU.getCommand(), new Cat(sendMessageService));
    }

    public Command command(String command) {
        return commandMap.get(command);
    }
}
