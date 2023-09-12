package skypro.TeamWorkTelegramBot.buttons;

import org.springframework.stereotype.Component;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.stages.EntryStage0;
import skypro.TeamWorkTelegramBot.stages.Start;

import java.util.HashMap;
import java.util.Map;

import static skypro.TeamWorkTelegramBot.buttons.Keyboards.*;

@Component
public class CommandKeysStorage {
    private final SendMessageService sendMessageService;
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandKeysStorage (SendMessageService sendMessageService1) {
        this.sendMessageService = sendMessageService1;
        commandMap.put(START.getCommand(), new Start(sendMessageService));
        commandMap.put(DOG.getCommand(), new EntryStage0(sendMessageService));
        commandMap.put(CAT.getCommand(), new EntryStage0(sendMessageService));
    }

    public Command command(String command) {
        return commandMap.get(command);
    }
}
