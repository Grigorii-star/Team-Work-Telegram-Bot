package skypro.TeamWorkTelegramBot.buttons;


public enum Keyboards {
    START("start"),
    DOG("собака"),
    CAT("кошка"),
    STAGE_2("stage2"),
    STAGE_3("stage3");


    private final String command;

    Keyboards(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
