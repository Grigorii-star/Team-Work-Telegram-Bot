package skypro.TeamWorkTelegramBot.buttons.stages.get;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import skypro.TeamWorkTelegramBot.buttons.CommandAbstractClass;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.message.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegram.TelegramBotService;

import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsCallData.*;
import static skypro.TeamWorkTelegramBot.buttons.constants.ConstantsText.getInfo;

/**
 * Класс отвечает за логику кнопок из класса GetAnimalFromTheShelter.
 *
 * @see GetAnimalFromTheShelter
 */
@Component
public class CatAndDogGetAnimalFromTheShelter extends CommandAbstractClass {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;

    public CatAndDogGetAnimalFromTheShelter(SendMessageService sendMessageService,
                                            AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    /**
     * Метод обрабатывает разные варианты callbackQuery, отправляя разные сообщения для
     * пользователям в зависимости от выбранной опции.
     *
     * @param callbackQuery  - объект Telegram для получения значений из Telegram бота.
     * @param telegramBotService - объект передается в SendMessageService для возможности
     *                             вызвать метод execute и отправить сообщение пользователю.
     * @see SendMessageService
     */
    @Override
    public void callBackQueryExtractor(CallbackQuery callbackQuery, TelegramBotService telegramBotService) {
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(callbackQuery.getFrom().getId());

        switch (callbackQuery.getData()) {
            case MEETING_DOG_RULES:
            case MEETING_CAT_RULES:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage2/dog-rules.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage2/cat-rules.txt"),
                            telegramBotService
                    );
                }
                break;
            case DOC_LIST:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/doc-list.txt"),
                        telegramBotService
                );
                break;
            case TRANSPORTATION:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/transfer.txt"),
                        telegramBotService
                );
                break;
            case PUPPY_HOUSE:
            case PUSSY_HOUSE:
                if (animalOwner.getDogLover()) {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage2/doggy-house.txt"),
                            telegramBotService
                    );
                } else {
                    sendMessageService.SendMessageToUser(
                            String.valueOf(callbackQuery.getFrom().getId()),
                            getInfo("src/main/resources/bot-files/stage2/kitten-house.txt"),
                            telegramBotService
                    );
                }
                break;
            case PET_HOUSE:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/adult-pet-house.txt"),
                        telegramBotService
                );
                break;
            case INVALID_HOUSE:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/invalid-adult-pet-house.txt"),
                        telegramBotService
                );
                break;
            case DOG_HANDLER_ADVICE:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/first_time_with_dog.txt"),
                        telegramBotService
                );
                break;
            case DOG_HANDLER_CONTACTS:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/dog_handlers_contact.txt"),
                        telegramBotService
                );
                break;
            case REFUSAL_REASONS:
                sendMessageService.SendMessageToUser(
                        String.valueOf(callbackQuery.getFrom().getId()),
                        getInfo("src/main/resources/bot-files/stage2/refuse-reasons.txt"),
                        telegramBotService
                );
                break;
        }
    }
}
