package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

@Component
public class HelpVolunteer extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;

    public HelpVolunteer(SendMessageService sendMessageService,
                         AnimalOwnerRepository animalOwnerRepository,
                         VolunteersRepository volunteersRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
    }

    @Override
    public void messagesExtractor(Message message, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(message.getFrom().getId());

        if (animalOwner.getIsVolunteer()) {
            Volunteer volunteer = volunteersRepository.findByIdChat(message.getFrom().getId());
            if (volunteer.getAnimalOwner() != null) {
                System.out.println("отправляется сообщение от волонтера пользователю HelpVolunteer");
                sendMessageService.SendMessageToUser(
                        String.valueOf(volunteer.getAnimalOwner().getIdChat()),
                        message.getText(),
                        telegramBotService
                );
            }
        }

        if (!animalOwner.getIsVolunteer()) {
            if (animalOwner.getVolunteer() != null) {
                System.out.println("отправляется сообщение от пользователя волонтёру HelpVolunteer");
                sendMessageService.SendMessageToUser( // отправляется сообщение волонтёру
                        String.valueOf(animalOwner.getVolunteer().getIdChat()), //заменить на volunteerId
                        message.getText(),
                        telegramBotService
                );
            }
        }
    }
}
