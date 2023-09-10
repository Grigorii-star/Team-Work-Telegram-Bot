package skypro.TeamWorkTelegramBot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.stages.ConsultationStage1;
import skypro.TeamWorkTelegramBot.stages.EntryStage0;
import skypro.TeamWorkTelegramBot.stages.StageOfPreparationOfDocuments2;
import skypro.TeamWorkTelegramBot.stages.StageSelector;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotService implements UpdatesListener {


    private final AnimalOwnerRepository animalOwnerRepository;
    private final TelegramBot telegramBot;
    private final EntryStage0 entryStage0;
    private final ConsultationStage1 consultationStage1;
    private final StageOfPreparationOfDocuments2 stageOfPreparationOfDocuments2;
    private final StageSelector stageSelector;

    @Autowired
    public TelegramBotService(TelegramBot telegramBot,
                              AnimalOwnerRepository animalOwnerRepository,
                              EntryStage0 entryStage0,
                              ConsultationStage1 consultationStage1,
                              StageOfPreparationOfDocuments2 stageOfPreparationOfDocuments2,
                              StageSelector stageSelector) {
        this.telegramBot = telegramBot;
        this.animalOwnerRepository = animalOwnerRepository;
        this.entryStage0 = entryStage0;
        this.consultationStage1 = consultationStage1;
        this.stageOfPreparationOfDocuments2 = stageOfPreparationOfDocuments2;
        this.stageSelector = stageSelector;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {

            Long chatId = update.message().chat().id();
            String text = update.message().text();
            String userName = update.message().from().firstName();

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
                    telegramBot.execute(entryStage0.greetingNewOwnerMessage(chatId, userName));
                    telegramBot.execute(entryStage0.aboutShelterMessage(chatId));
                    telegramBot.execute(entryStage0.animalChoice(chatId));
                } else if ((currentStage == 0 || currentStage > 0) && (animalOwner.getIdChat() != null)) {
                    animalOwner.setDogLover(null);
                    animalOwnerRepository.save(animalOwner);
                    stageSelector.switchToStage0(chatId);
                    telegramBot.execute(entryStage0.animalChoice(chatId));
                }

            } else if (currentStage == 0 && animalOwner.getDogLover() == null) {
                switch (text) {
                    case "1": {
                        animalOwner.setDogLover(true);
                        animalOwnerRepository.save(animalOwner);
                        stageSelector.switchToStage0(chatId);
                        telegramBot.execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                    }
                    case "2": {
                        animalOwner.setDogLover(false);
                        animalOwnerRepository.save(animalOwner);
                        stageSelector.switchToStage0(chatId);
                        telegramBot.execute(entryStage0.makeAChoiceOfStage0(chatId));
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
                        telegramBot.execute(entryStage0.animalChoice(chatId));
                        break;
                }
                SendMessage messageStage0 = new SendMessage(chatId, message1);
                telegramBot.execute(messageStage0);
                if (!message2.isEmpty()) {
                    SendMessage messageStage1 = new SendMessage(chatId, message2);
                    telegramBot.execute(messageStage1);
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
                        telegramBot.execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                }
                SendMessage messageStage0 = new SendMessage(chatId, message);
                telegramBot.execute(messageStage0);
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
                            telegramBot.execute(entryStage0.makeAChoiceOfStage0(chatId));
                        }
                        break;
                    case "11":
                        if (animalOwner.getDogLover()) {
                            message = stageOfPreparationOfDocuments2.callAVolunteer();
                        }
                        break;
                    case "12":
                        stageSelector.switchToStage0(chatId);
                        telegramBot.execute(entryStage0.makeAChoiceOfStage0(chatId));
                        break;
                }
                SendMessage messageStage0 = new SendMessage(chatId, message);
                telegramBot.execute(messageStage0);
            }

            else if (currentStage == 3) {

            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
