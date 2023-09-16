package skypro.TeamWorkTelegramBot.service.fileService;

import org.telegram.telegrambots.meta.api.objects.Message;
import skypro.TeamWorkTelegramBot.entity.Report;

public interface FileService {
    Report animalReport (Message telegramMessage);
}
