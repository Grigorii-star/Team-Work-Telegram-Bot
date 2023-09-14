package skypro.TeamWorkTelegramBot.buttons.stages.GetAnimal;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.buttons.Command;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.SendMessageService;
import skypro.TeamWorkTelegramBot.service.TelegramBotService;

@Component
public class GetAnimalFromTheShelter implements Command {
    private final SendMessageService sendMessageService;
    private final AnimalOwnerRepository animalOwnerRepository;
    private TelegramBotService telegramBotService;

    public final static String GREETING_MESSAGE = "Я рад, что ты готов к встрече с новым членом семьи!\n" +
            "Для продолжения, пожалуйста, ознакомься со следующей документацией.";

    public GetAnimalFromTheShelter(SendMessageService sendMessageService,
                                   AnimalOwnerRepository animalOwnerRepository) {
        this.sendMessageService = sendMessageService;
        this.animalOwnerRepository = animalOwnerRepository;
    }

    String[] buttonsTextDog = {
            "Правила знакомства с собакой до того, как взять его из приюта",
            "Список документов, необходимых для того, чтобы взять собаку из приюта",
            "Список рекомендаций по транспортировке собаки",
            "Список рекомендаций по обустройству дома для щенка",
            "Список рекомендаций по обустройству дома для взрослого животного",
            "Список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)",
            "Советы кинолога по первичному общению с собакой",
            "Контактные данные проверенных кинологов",
            "Список причин, почему могут отказать выдать собаку из приюта",
            "Оставить контактные данные, чтобы волонтер мог связаться",
            "Позвать волонтера",
            "Перейти в главное меню"};

    String[] buttonsCallDataDog = {
            "правила_знакомства_собака",
            "список_документов",
            "транспортировка",
            "дом_для_щенка",
            "дом_для_животного",
            "дом_для_инвалида",
            "советы_кинолога",
            "контакты_кинолога",
            "причина_отказа",
            "контакт",
            "волонтер",
            "меню"};

    String[] buttonsTextCat = {
            "Правила знакомства с кошкой до того, как взять его из приюта",
            "Список документов, необходимых для того, чтобы взять кошку из приюта",
            "Список рекомендаций по транспортировке кошки",
            "Список рекомендаций по обустройству дома для котенка",
            "Список рекомендаций по обустройству дома для взрослого животного",
            "Список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)",
            "Список причин, почему могут отказать выдать кошку из приюта",
            "Оставить контактные данные, чтобы волонтер мог связаться",
            "Позвать волонтера",
            "Перейти в главное меню"};
    String[] buttonsCallDataCat = {
            "правила_знакомства_кошка",
            "список_документов",
            "транспортировка",
            "дом_для_котенка",
            "дом_для_животного",
            "дом_для_инвалида",
            "причина_отказа",
            "контакт",
            "волонтер",
            "меню"};

    /**
     * данный метод отправляет приветственное сообщение с кнопками в зависимости от того, является
     * ли пользователь любителем собак или кошек, полученных в параметре Update.
     * @param update объект телеграмма для получения значений из телеграмм бота
     * @param telegramBotService
     */
    @Override
    public void execute(Update update, TelegramBotService telegramBotService) {
        Long chatId = update.getCallbackQuery().getFrom().getId();
        AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);

        if (animalOwner.getDogLover()) {
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsTextDog,
                    buttonsCallDataDog,
                    telegramBotService
            );
        }
        else {
            sendMessageService.SendMessageToUser(
                    String.valueOf(chatId),
                    GREETING_MESSAGE,
                    buttonsTextCat,
                    buttonsCallDataCat,
                    telegramBotService
            );
        }
    }
}
