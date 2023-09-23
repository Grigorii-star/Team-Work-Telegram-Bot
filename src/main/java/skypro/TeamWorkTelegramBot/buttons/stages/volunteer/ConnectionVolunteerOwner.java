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


import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsButtons.INTERRUPT_CHAT_BUTTON;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.CHAT;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCommands.REPORT_CONNECT_TO_USER_COMMAND;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.ABOUT_REPORT_TO_USER_MESSAGE;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.ABOUT_REPORT_TO_VOLUNTEER_MESSAGE;

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

            if (message.getText().contains(REPORT_CONNECT_TO_USER_COMMAND)) {
                String resultId = message.getText().replaceAll(REPORT_CONNECT_TO_USER_COMMAND, "");

                System.out.println("отправляется сообщение от волонтера пользователю ConnectionVolunteerOwner");

                sendMessageService.SendMessageToUser(
                        String.valueOf(resultId),
                        ABOUT_REPORT_TO_USER_MESSAGE,
                        telegramBotService
                );

                sendMessageService.SendMessageToUserWithButtons(
                        String.valueOf(animalOwner.getIdChat()),
                        ABOUT_REPORT_TO_VOLUNTEER_MESSAGE + resultId,
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
