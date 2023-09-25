package skypro.TeamWorkTelegramBot.service;

import liquibase.pro.packaged.H;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import skypro.TeamWorkTelegramBot.entity.AnimalOwner;
import skypro.TeamWorkTelegramBot.entity.BinaryContent;
import skypro.TeamWorkTelegramBot.entity.Report;
import skypro.TeamWorkTelegramBot.repository.AnimalOwnerRepository;
import skypro.TeamWorkTelegramBot.repository.DateAndTimeReportRepository;
import skypro.TeamWorkTelegramBot.repository.ReportsRepository;
import skypro.TeamWorkTelegramBot.service.file.FileServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    AnimalOwnerRepository animalOwnerRepository;

    @Mock
    ReportsRepository reportsRepository;

    @Mock
    DateAndTimeReportRepository dateAndTimeReportRepository;

    @Mock
    Message telegramMessage;

    @Mock
    PhotoSize photoSize;

    @Mock
    RestTemplate restTemplate;

    @Value("${telegram.bot.token}")
    String token;

    @InjectMocks
    FileServiceImpl fileService;

    @Test
    void indexOutOfBoundGetPhoto() {
        Message tMessage = new Message();
        tMessage.setPhoto(List.of(new PhotoSize(), new PhotoSize()));
        Assertions.assertThrows(IndexOutOfBoundsException.class, ()-> fileService.animalReport(tMessage));
    }

    @Test
    void dateAndTimeNullPointer() {

        Message tMessage = new Message();
        tMessage.setPhoto(List.of(new PhotoSize(), new PhotoSize(),new PhotoSize()));
        tMessage.setChat(new Chat(1L,""));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> fileService.animalReport(tMessage));
    }

}