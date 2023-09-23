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

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.INTERRUPT_CHAT_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.CHAT;

@Component
public class ConnectionVolunteerOwner extends CommandAbstractClass {

    private final AnimalOwnerRepository animalOwnerRepository;
    private final VolunteersRepository volunteersRepository;
    private final SendMessageService sendMessageService;

    public ConnectionVolunteerOwner(AnimalOwnerRepository animalOwnerRepository,
                                    VolunteersRepository volunteersRepository,
                                    SendMessageService sendMessageService) {
        this.animalOwnerRepository = animalOwnerRepository;
        this.volunteersRepository = volunteersRepository;
        this.sendMessageService = sendMessageService;
    }

    String[] buttonsText = {INTERRUPT_CHAT_BUTTON};
    String[] buttonsCallData = {CHAT};

    @Override
    public void messagesExtractor(Message message, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(message.getFrom().getId());

        if (animalOwner.getIsVolunteer()) {
            Volunteer volunteer = volunteersRepository.findByIdChat(message.getFrom().getId());

            if (message.getText().contains("-")) {
                String resultId = message.getText().replaceAll("-", "");
                System.out.println("отправляется сообщение от волонтера пользователю ConnectionVolunteerOwner");
                sendMessageService.SendMessageToUser(
                        String.valueOf(resultId),
                        "С тобой сейчас свяжется волонтёр по поводу отчётов",
                        telegramBotService
                );

                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(animalOwner.getIdChat()),
                        "Сейчас ты пишешь владельцу с чатом: " + resultId,
                        buttonsText,
                        buttonsCallData,
                        telegramBotService
                );
                AnimalOwner idChatOwner = animalOwnerRepository.findByIdChat(Long.valueOf(resultId));
                volunteer.setAnimalOwner(idChatOwner);
                volunteer.setIsBusy(true);
                animalOwner.setInChat(true);
                idChatOwner.setHelpVolunteer(true);
                idChatOwner.setVolunteer(volunteer);
                idChatOwner.setInChat(true);
                volunteersRepository.save(volunteer);
                animalOwnerRepository.save(idChatOwner);
                animalOwnerRepository.save(animalOwner);
            }
        }
    }
}
