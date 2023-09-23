package skypro.TeamWorkTelegramBot.buttons.constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Класс содержит константы для сообщений.
 */
public class ConstantsText {
    public final static String START_GREETING_MESSAGE = "Привет! Я бот, который поможет Вам забрать питомца из нашего приюта в Астане. " +
                                                         "Я отвечу на все вопросы и помогу определиться с выбором.";
    public final static String CHOOSE_A_SHELTER_OR_BECOME_A_VOLUNTEER_MESSAGE = "Можете выбрать приют или стать волонтером.";
    public final static String BECOME_VOLUNTEER_MESSAGE = "Спасибо за Вашу готовность помогать!\n";
    public final static String CALL_VOLUNTEER_TO_USER_MESSAGE = "Напишите свой вопрос волонтёру, и он в ближайшее время Вам ответит.";
    public final static String CALL_VOLUNTEER_TO_VOLUNTEER_MESSAGE = "Сейчас с Вами свяжется пользователь.";
    public final static String INTERRUPT_CHAT_WITH_VOLUNTEER_MESSAGE = "Связь с волонтером прервана.";
    public final static String INTERRUPT_CHAT_WITH_USER_MESSAGE = "Связь с пользователем прервана.";
    public final static String ABOUT_REPORT_TO_USER_MESSAGE = "С Вами сейчас свяжется волонтёр по поводу отчётов.";
    public final static String ABOUT_REPORT_TO_VOLUNTEER_MESSAGE = "Сейчас Вы пишете владельцу с чатом: ";
    public final static String SAVE_CONTACT_WITH_MENU_SUCCESSFULLY_MESSAGE = "Отлично! Я сохранил Ваши контактные данные.\n" +
                                                                         "Для перехода в главное меню нажмите на кнопку ниже.";
    public final static String SAVE_CONTACT_WITH_START_SUCCESSFULLY_MESSAGE = "Отлично! Я сохранил Ваши контактные данные.\n" +
                                                                        "Для перехода в меню выбора приюта нажмите на /start";
    public final static String SAVE_CONTACT_MESSAGE = "Введите свои контактные данные в формате:\n" +
                                                    "+7 999 999 99 99";
    public final static String SAVE_REPORT_MESSAGE = "Отправьте фото с прикрепленным текстовым отчетом. \n" +
                                                    "Отчет должен содержать: \n" +
                                                    "Рацион животного. \n" +
                                                    "Общее самочувствие и привыкание к новому месту. \n" +
                                                    "Изменение в поведении: отказ от старых привычек, приобретение новых.";
    public final static String SAVE_REPORT_SUCCESSFULLY_MESSAGE = "Фото и отчет успешно загружены. Перейдите в главное меню.";
    public final static String SAVE_REPORT_ERROR_MESSAGE = "К сожалению загрузка фото и отчета не удалась.";
    public final static String SAVE_REPORT_INVALID_MESSAGE = "Неверный формат фото и отчета.";
    public final static String SAVE_REPORT_ONE_DAY_ERROR_TO_USER_MESSAGE = "Вы не прислали отчет о питомце. Отправьте отчёт.";
    public final static String SAVE_REPORT_TWO_DAYS_ERROR_TO_USER_MESSAGE = "Вы не присылали отчёт более двух дней! С Вами свяжется волонтёр";
    public final static String SAVE_REPORT_TWO_DAYS_ERROR_TO_VOLUNTEER_MESSAGE = "Владелец животного не присылает отчёт два дня! Для связи с владельцем" +
                                                                                 " напишите его chatId в формате: -";
    public final static String SAVE_REPORT_THIRTY_DAYS_SUCCESSFULLY_TO_VOLUNTEER_MESSAGE = "Владелец животного 30 дней присылает отчёт! Для связи с владельцем" +
                                                                                " напишите его chatId в формате: -";
    public final static String SAVE_REPORT_FOURTEEN_ADDITIONAL_DAYS_SUCCESSFULLY_TO_VOLUNTEER_MESSAGE = "У владелеца животного прошло 14 дополнительных дней испытательного срока! Для связи с владельцем" +
                                                                                " напишите его chatId в формате: -";
    public final static String SAVE_REPORT_THIRTY_ADDITIONAL_DAYS_SUCCESSFULLY_TO_VOLUNTEER_MESSAGE = "У владелеца животного прошло 30 дополнительных дней испытательного срока! Для связи с владельцем" +
                                                                                " напишите его chatId в формате: -";


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
