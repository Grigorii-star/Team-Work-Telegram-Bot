package skypro.TeamWorkTelegramBot.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TelegramBotServiceTest {

    @LocalServerPort
    private int port;

    @Mock
    AnimalOwnerRepository animalOwnerRepository;

    @Mock
    Update update;

    @Mock
    Message message;


    @Autowired
    TelegramBotService telegramBotService;

    @BeforeAll
    static void init() {

    }

    @Test
    void onUpdateReceived() {


        when(update.getMessage()).thenReturn(message);
        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage().hasText()).thenReturn(true);
        when(update.getMessage().getChatId()).thenReturn(1L);
        when(update.getMessage().getText()).thenReturn("test");
        when(animalOwnerRepository.findByIdChat(anyLong())).thenReturn(null);

        telegramBotService.onUpdateReceived(update);
        verify(animalOwnerRepository, times(2));
    }


}