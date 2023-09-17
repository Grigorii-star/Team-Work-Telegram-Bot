package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.stages.saves.SaveUserContacts.GREETING_MESSAGE;

@Component
public class BecomeVolunteer implements Command {

    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public BecomeVolunteer(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
        if (!animalOwner.getBeVolunteer()) {

            animalOwner.setBeVolunteer(true);
            animalOwner.setCanSaveContact(true);


            animalOwnerRepository.save(animalOwner);
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    "Спасибо за твою готовность помогать!\n" +
                            GREETING_MESSAGE,
                    telegramBotService
            );
        }
    }
}
