package skypro.TeamWorkTelegramBot.stages;

public class EntryStage {

    private String chooseAShelter() {
        return "";
    }

    private String getInformationAboutTheShelter() { //аналогичный метод в ConsultationStage: talkAboutShelter()
        return ""; //возможно тут делать переадресацию на этап(1) ConsultationStage
    }

    private String informationAboutTakingAnAnimal() {
        return ""; //возможно тут делать переадресацию на этап(2) StageOfPreparationOfDocuments
    }

    private String submitAPetReport() {
        return ""; //возможно тут делать переадресацию на этап(3) CareStage
    }

    private String callAVolunteer() { //этот метот присутствует на всех этапах
        return "";
    }
}
