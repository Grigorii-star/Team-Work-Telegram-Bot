package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

@Component
public class InterruptChat extends CommandAbstractClass {

    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;


    public InterruptChat(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository, VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
    }

    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {

        //чат ай ди пользователя
        Long chatId = callbackQuery.getFrom().getId();

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        //Long volunteerId = getChatIdVolunteer();
        Volunteer volunteer = volunteersRepository.findByAnimalOwner(animalOwner);
        interruptChat(animalOwner, volunteer);

    }

    private void interruptChat(AnimalOwner animalOwner, Volunteer volunteer) {
        animalOwner.setHelpVolunteer(false); //устанавливаем что владельцу сейчас помогает волонтер
        animalOwner.setVolunteer(null); // устанавливаем его волонтера
        volunteer.setIsBusy(false); // волонтеру ставим, что занят
        volunteer.setAnimalOwner(null); // волонтеру ставим его владельца
        //сохраняем в базу данных все
        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);
    }


}
