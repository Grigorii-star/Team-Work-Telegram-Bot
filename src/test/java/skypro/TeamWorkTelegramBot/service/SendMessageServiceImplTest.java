package skypro.TeamWorkTelegramBot.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import skypro.TeamWorkTelegramBot.service.sendMessageService.SendMessageService;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SendMessageServiceImplTest {


    @LocalServerPort
    private int port;

    @Mock
    TelegramBotService telegramBotService;

    @Mock
    SendMessage sendMessage;

    @Autowired
    SendMessageService sendMessageService;

    @BeforeAll
    static void init() {

    }

    @Test
    void sendMessageToUserBothMethods() throws TelegramApiException {
        String[] buttonText = {"Да", "Нет"};
        String[] buttonCallData = {"Правда", "Ложь"};

        sendMessageService.SendMessageToUser("1","text",telegramBotService);
        sendMessageService.SendMessageToUserWithButtons("1","text",buttonText, buttonCallData,telegramBotService);
        verify(telegramBotService, times(2));

//        when(telegramBotService.execute(any(SendMessage.class))).thenThrow(new TelegramApiException());
//
//        sendMessageService.SendMessageToUser("1","text",telegramBotService);
//        Assertions.assertDoesNotThrow(()->sendMessageService);



    }

}