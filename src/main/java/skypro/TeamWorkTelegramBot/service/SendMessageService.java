package skypro.TeamWorkTelegramBot.service;

public interface SendMessageService {
    void SendMessageToUser(String chatId, String textToSend, String[] buttonsText, String[] buttonsCallData);
    void SendMessageToUser(String chatId, String textToSend);
}
