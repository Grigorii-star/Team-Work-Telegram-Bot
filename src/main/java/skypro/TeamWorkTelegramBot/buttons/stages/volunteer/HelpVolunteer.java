package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

/**
 * Класс обрабатывает входящие сообщения от пользователя и волонтера.
 */
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

    /**
     * Метод принемает входящие сообщения от пользователя и волонтера, а также
     * перенаправляет их друг-другу.
     *
     * @param message - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     */
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
