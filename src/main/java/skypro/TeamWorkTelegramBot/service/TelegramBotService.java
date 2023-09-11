package skypro.TeamWorkTelegramBot.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.configuration.TelegramBotConfiguration;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.stages.ConsultationStage1;
import skypro.TeamWorkTelegramBot.stages.EntryStage0;
import skypro.TeamWorkTelegramBot.stages.StageOfPreparationOfDocuments2;
import skypro.TeamWorkTelegramBot.stages.StageSelector;


@Service
public class TelegramBotService extends TelegramLongPollingBot {
    private final TelegramBotConfiguration telegramBotConfiguration;
    private final AnimalOwnerRepository animalOwnerRepository;
    private final EntryStage0 entryStage0;
    private final ConsultationStage1 consultationStage1;
    private final StageOfPreparationOfDocuments2 stageOfPreparationOfDocuments2;
    private final StageSelector stageSelector;

    @Autowired
    public TelegramBotService(TelegramBotConfiguration telegramBotConfiguration,
                              AnimalOwnerRepository animalOwnerRepository,
                              EntryStage0 entryStage0,
                              ConsultationStage1 consultationStage1,
                              StageOfPreparationOfDocuments2 stageOfPreparationOfDocuments2,
                              StageSelector stageSelector) {
        this.telegramBotConfiguration = telegramBotConfiguration;
        this.animalOwnerRepository = animalOwnerRepository;
        this.entryStage0 = entryStage0;
        this.consultationStage1 = consultationStage1;
        this.stageOfPreparationOfDocuments2 = stageOfPreparationOfDocuments2;
        this.stageSelector = stageSelector;
    }

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getName();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfiguration.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            String userName = update.getMessage().getChat().getUserName();

            stageSelector.ifFirstTime(chatId);
            AnimalOwner animalOwner = animalOwnerRepository.findByIdChat(chatId);
            Integer currentStage = animalOwner.getStage();
            System.out.println(chatId);
            System.out.println(text);
            System.out.println(currentStage);
            System.out.println(animalOwner);

            if (text.equals("/start")) {
                if (currentStage == -1) {
                    stageSelector.switchToStage0(chatId);
                    execute(entryStage0.greetingNewOwnerMessage(chatId, userName));
                    execute(entryStage0.aboutShelterMessage(chatId));
                    execute(entryStage0.animalChoice(chatId));
                } else if ((currentStage == 0 || currentStage > 0) && (animalOwner.getIdChat() != null)) {
                    animalOwner.setDogLover(null);
                    animalOwnerRepository.save(animalOwner);
                    stageSelector.switchToStage0(chatId);
                    execute(entryStage0.animalChoice(chatId));
                }

            } else if (currentStage == 0 && animalOwner.getDogLover() == null) {
                switch (text) {
                    case "1": {
                        animalOwner.setDogLover(true);
                        animalOwnerRepository.save(animalOwner);
                        stageSelector.switchToStage0(chatId);
                        execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                    }
                    case "2": {
                        animalOwner.setDogLover(false);
                        animalOwnerRepository.save(animalOwner);
                        stageSelector.switchToStage0(chatId);
                        execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                    }
                }

            } else if (currentStage == 0) {
                String message1 = "";
                String message2 = "";
                switch (text) {
                    case "1":
                        message1 = entryStage0.getInformationAboutTheShelterGreeting();
                        message2 = entryStage0.getInformationAboutTheShelterChooseOfStage();
                        stageSelector.switchToStage1(chatId);
                        break;
                    case "2":
                        message1 = entryStage0.informationAboutTakingAnAnimalShelterGreeting();
                        message2 = entryStage0.informationAboutTakingAnAnimalShelterChooseOfStage(chatId);
                        stageSelector.switchToStage2(chatId);
                        break;
                    case "3":
                        message1 = entryStage0.submitAPetReport();
                        stageSelector.switchToStage3(chatId);
                        break;
                    case "4":
                        message1 = entryStage0.callAVolunteer();
                        break;
                    case "5":
                        animalOwner.setDogLover(null);
                        animalOwnerRepository.save(animalOwner);
                        stageSelector.switchToStage0(chatId);
                        execute(entryStage0.animalChoice(chatId));
                        break;
                }
                SendMessage messageStage0 = new SendMessage(String.valueOf(chatId), message1);
                execute(messageStage0);
                if (!message2.isEmpty()) {
                    SendMessage messageStage1 = new SendMessage(String.valueOf(chatId), message2);
                    execute(messageStage1);
                }

            } else if (currentStage == 1) {
                String message = "";
                switch (text) {
                    case "1":
                        message = consultationStage1.getInformationAboutTheShelter(chatId);
                        break;
                    case "2":
                        message = consultationStage1.giveOutTheSheltersWorkScheduleAndAddressAndDirections(chatId);
                        break;
                    case "3":
                        message = consultationStage1.provideSecurityContactInformation(chatId);
                        break;
                    case "4":
                        message = consultationStage1.issueGeneralSafetyAdvice();
                        break;
                    case "5":
                        message = consultationStage1.acceptAndRecordContactDetails(chatId);
                        break;
                    case "6":
                        message = consultationStage1.callAVolunteer();
                        break;
                    case "7":
                        stageSelector.switchToStage0(chatId);
                        execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                }
                SendMessage messageStage0 = new SendMessage(String.valueOf(chatId), message);
                execute(messageStage0);
            }

            else if (currentStage == 2) {
                String message = "";
                switch (text) {
                    case "1":
                        message = stageOfPreparationOfDocuments2.issueRules(chatId);
                        break;
                    case "2":
                        message = stageOfPreparationOfDocuments2.issueAListOfDocumentsInOrderToTakeTheAnimal();
                        break;
                    case "3":
                        message = stageOfPreparationOfDocuments2.issueAListOfRecommendationsForTransportation();
                        break;
                    case "4":
                        message = stageOfPreparationOfDocuments2.issueAListOfRecommendationsForHomeImprovementForAPuppyOrKitten(chatId);
                        break;
                    case "5":
                        message = stageOfPreparationOfDocuments2.issueAListOfRecommendationsForHomeImprovementForAnAdultAnimal();
                        break;
                    case "6":
                        message = stageOfPreparationOfDocuments2.issueAListOfRecommendationsForHomeImprovementForAnAnimalWithDisabilities();
                        break;
                    case "7":
                        if (animalOwner.getDogLover()) {
                            message = stageOfPreparationOfDocuments2.giveCynologistAdviceOnInitialCommunicationWithADog();
                        } else {
                            message = stageOfPreparationOfDocuments2.issueAListOfReasonsForRefusal();
                        }
                        break;
                    case "8":
                        if (animalOwner.getDogLover()) {
                            message = stageOfPreparationOfDocuments2.issueRecommendationsOnProvenCynologistsForFurtherReferralToThem();
                        } else {
                            message = stageOfPreparationOfDocuments2.acceptAndRecordContactDetails();
                        }
                        break;
                    case "9":
                        if (animalOwner.getDogLover()) {
                            message = stageOfPreparationOfDocuments2.issueAListOfReasonsForRefusal();
                        } else {
                            message = stageOfPreparationOfDocuments2.callAVolunteer();
                        }
                        break;
                    case "10":
                        if (animalOwner.getDogLover()) {
                            message = stageOfPreparationOfDocuments2.acceptAndRecordContactDetails();
                        } else {
                            stageSelector.switchToStage0(chatId);
                            execute(entryStage0.makeAChoiceOfStage0(chatId));
                        }
                        break;
                    case "11":
                        if (animalOwner.getDogLover()) {
                            message = stageOfPreparationOfDocuments2.callAVolunteer();
                        }
                        break;
                    case "12":
                        stageSelector.switchToStage0(chatId);
                        execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                }
                SendMessage messageStage0 = new SendMessage(String.valueOf(chatId), message);
                execute(messageStage0);
            }

            else if (currentStage == 3) {

            }
        }
    }
}
