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
import skypro.TeamWorkTelegramBot.service.restApiServices.VolunteerService;

import java.util.*;

@Component
public class HelpVolunteer implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;
    private final VolunteerService volunteerServicer;


    public HelpVolunteer(SendMessageService sendMessageService, AnimalOwnerRepository animalOwnerRepository, VolunteersRepository volunteersRepository, VolunteerService volunteerServicer) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
        this.volunteerServicer = volunteerServicer;
    }

    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatIdOwner = update.getMessage().getFrom().getId(); //userId
        String text = update.getMessage().getText(); //userText

        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatIdOwner);

        //Long volunteerId = getChatIdVolunteer();
        //Volunteer volunteer = volunteersRepository.findByIdChat(volunteerId);

        //List<List<Volunteer>> listAll = Collections.singletonList(new ArrayList<Volunteer>(volunteersRepository.findAll()));

//        Random random = new Random();
//        int index = random.nextInt(listAll.size());

        if ((animalOwner.getHelpVolunteer() && !animalOwner.getBeVolunteer())) {

            System.out.println("отпрвляется сообщение волонтёру");
            sendMessageService.SendMessageToUser( // отпрвляется сообщение волонтёру
                    String.valueOf(37909760L), //заменить на volunteerId
                    text,
                    telegramBotService
            );
        }

        if (!animalOwner.getHelpVolunteer() && animalOwner.getBeVolunteer()) {

            System.out.println("отпрвляется сообщение пользователю");
            sendMessageService.SendMessageToUser( //отпрвляется сообщение пользователю
                    String.valueOf(chatIdOwner), //заменить на userId, который был сохранён в БД
                    text,
                    telegramBotService
            );
        }
    }

    private Long getChatIdVolunteer() {
        List<Volunteer> collectList = new ArrayList<>(volunteersRepository.findAll()); // вытащить id
        //collectList.stream()
        List<Long> chatIds = List.of(37909760L, 6238970921L);
        Random random = new Random();

        int index = random.nextInt(chatIds.size());
        //int index2 = random.nextInt(collectList.size());
        return chatIds.get(index);
    }

}
