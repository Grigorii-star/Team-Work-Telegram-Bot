package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

@Component
public class InterruptChat implements Command {

    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;


    public InterruptChat(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository, VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {

        //чат ай ди пользователя
        Long chatId = update.getCallbackQuery().getFrom().getId();

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        //Long volunteerId = getChatIdVolunteer();
        Volunteer volunteer = volunteersRepository.findByAnimalOwner(animalOwner);
//        animalOwner.setHelpVolunteer(true); //устанавливаем что владельцу сейчас помогает волонтер
        animalOwner.setVolunteer(volunteer); // устанавливаем его волонтера
        volunteer.setIsBusy(true); // волонтеру ставим, что занят
        volunteer.setAnimalOwner(animalOwner); // волонтеру ставим его владельца
        //сохраняем в базу данных все
        animalOwnerRepository.save(animalOwner);
        volunteersRepository.save(volunteer);

    }





}
