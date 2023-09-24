package skypro.TeamWorkTelegramBot.exception;

/**
 * Класс самописных исключений для загрузки отчетов о животных в БД.
 */
public class UploadFileException extends RuntimeException{
    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }
    public UploadFileException(String message) {
        super(message);
    }
    public UploadFileException(Throwable cause) {
        super(cause);
    }
}
