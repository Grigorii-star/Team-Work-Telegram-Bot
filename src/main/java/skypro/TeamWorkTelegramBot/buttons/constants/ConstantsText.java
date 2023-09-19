package skypro.TeamWorkTelegramBot.buttons.constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс содержит константы для сообщений.
 */
public class ConstantsText {

    //Константа приветствия, после кнопки старт
    public final static String GREETING_MESSAGE = "Привет! Я бот, который поможет тебе забрать питомца из нашего приюта в Астане. " +
            "Я отвечу на все вопросы и помогу определиться с выбором.";


    /**
     * Метод, который нужен для обработки текстовых файлов в String
     * @param filePath - принимает текстовый файл
     * @return String message
     * @throws IOException
     */

    public static String getInfo(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Файл не найден");
        }
        return sb.toString();
    }
}
