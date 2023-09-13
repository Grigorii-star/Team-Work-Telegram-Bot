package skypro.TeamWorkTelegramBot.buttons;


/**
 * Enum, где записаны все названия комнад в боте
 */
public enum Keyboards {
    START("start"),
    DOG("собака"),
    CAT("кошка"),


    INFO("инфо"),
    ADOPT_AN_ANIMAL("взять_животное"),
    REPORT("отчет"),
    VOLUNTEER("волонтер"),
    BACK("назад"),


    ABOUT_SHELTER("о_приюте"),
    SCHEDULE("расписание"),
    SECURITY("охрана"),
    SAFETY("техника_безопасности"),
    CONTACT("контакт"),
    MENU("меню"),


    //BACK("назад"),
    //BACK("назад"),
    STAGE_3("взять_живот");


    private final String command;

    Keyboards(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
