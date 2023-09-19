package skypro.TeamWorkTelegramBot.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.service.telegramBotService.TelegramBotService;

import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

    @Test
    public void startTest() {
        AnimalOwner animalOwner = new AnimalOwner(1, 1L, "contact", true,
                false, false, false, false, false,
                false, null, null);

        when(animalOwnerRepository.findByIdChat(1L)).thenReturn(animalOwner);
        when(animalOwnerRepository.save(animalOwner)).thenReturn(animalOwner);

        Long chatId = 1L;
        String textMessage = "/start";

        assertThat(chatId).isEqualTo(1L);
        assertThat(textMessage).isEqualTo("/start");
        assertThat(chatId).isNotEqualTo(555);
        assertThat(textMessage).isNotEqualTo("start");

        String patternNumber = "([\\+]?[7|8][\\s-(]?[9][0-9]{2}[\\s-)]?)?([\\d]{3})[\\s-]?([\\d]{2})[\\s-]?([\\d]{2})";

        String messageText = "+7 999 999 99 99";
        boolean matchesResult = Pattern.matches(patternNumber, messageText);

        assertThat(messageText).matches(patternNumber).isEqualTo(messageText);
    }
}