package skypro.TeamWorkTelegramBot.buttons.stages.volunteer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.Volunteer;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.VolunteersRepository;
import skypro.TeamWorkTelegramBot.service.restApiServices.VolunteerService;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

@Component
public class HelpVolunteer extends CommandAbstractClass {
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
    public void messagesExtractor(Message message, TelegramBotService telegramBotService) {
        Long chatId = message.getFrom().getId(); //userId
        String text = message.getText(); //userText


        //я ищу владельца (он может быть волонтером в баазе по чату)
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
//        Volunteer volunteer = volunteersRepository

        //проверяю не волонтер ли он
        if (animalOwner.getBeVolunteer()) {
            //если он волонтер, то мне нужно найти такого волонтера по айди его чата
            Volunteer volunteer = volunteersRepository.findByIdChat(chatId);
            if (volunteer.getAnimalOwner() != null) {
                System.out.println("отправляется сообщение от волонтера пользователю");
                sendMessageService.SendMessageToUser(
                        String.valueOf(volunteer.getAnimalOwner().getIdChat()),
                        text,
                        telegramBotService
                );
            }
        }

        // если это не волонтер, а будущий владелец, который хочет написать волонтеру:
        if (!animalOwner.getBeVolunteer()) {
            if (animalOwner.getVolunteer() != null) {
                System.out.println("отправляется сообщение от пользователя волонтёру");
                sendMessageService.SendMessageToUser( // отправляется сообщение волонтёру
                        String.valueOf(animalOwner.getVolunteer().getIdChat()), //заменить на volunteerId
                        text,
                        telegramBotService
                );
            }
        }
//        sendMessageService.SendMessageToUser(String.valueOf(chatIdOwner),
//                "Напишите сюда сообщение, волонтер на связи",
//                telegramBotService);


        //List<List<Volunteer>> listAll = Collections.singletonList(new ArrayList<Volunteer>(volunteersRepository.findAll()));

//        Random random = new Random();
//        int index = random.nextInt(listAll.size());

//        if ((animalOwner.getHelpVolunteer() && !animalOwner.getBeVolunteer())) {
//
//            System.out.println("отпрвляется сообщение волонтёру");
//            sendMessageService.SendMessageToUser( // отпрвляется сообщение волонтёру
//                    String.valueOf(317169588), //заменить на volunteerId
//                    text,
//                    telegramBotService
//            );
//        }

//        if (!animalOwner.getHelpVolunteer() && animalOwner.getBeVolunteer()) {
//
//            System.out.println("отпрвляется сообщение пользователю");
//            sendMessageService.SendMessageToUser( //отпрвляется сообщение пользователю
//                    String.valueOf(chatIdOwner), //заменить на userId, который был сохранён в БД
//                    text,
//                    telegramBotService
//            );
//        }
    }

//    private Long getChatIdVolunteer() {
//        List<Volunteer> collectList = new ArrayList<>(volunteersRepository.findAll()); // вытащить id
//        //collectList.stream()
//        List<Long> chatIds = List.of(37909760L, 6238970921L);
//        Random random = new Random();
//
//        int index = random.nextInt(chatIds.size());
//        //int index2 = random.nextInt(collectList.size());
//        return chatIds.get(index);
//    }


}
