package skypro.TeamWorkTelegramBot.stages;

public class ConsultationStage1 {


    private String userGreeting() { //аналогичный метод в StageOfPreparationOfDocuments
        return "Молодец, что перешёл в меню консультаций!\n" +
                "Для продолжения, пожалуйста, ознакомься с информацией о приюте.";
    }

    private String talkAboutShelter() { //аналогичный метод в EntryStage: getInformationAboutTheShelter()
        return "";
    }

    private String giveOutTheSheltersWorkScheduleAndAddressAndDirections() {
        return "";
    }

    private String provideSecurityContactInformation() {
        return "";
    }

    private String issueGeneralSafetyAdvice() {
        return "";
    }

    private String acceptAndRecordContactDetails() { //аналогичный метод в StageOfPreparationOfDocuments
        return "";
    }

    private String callAVolunteer() { //этот метот присутствует на всех этапах
        return "";
    }
}
