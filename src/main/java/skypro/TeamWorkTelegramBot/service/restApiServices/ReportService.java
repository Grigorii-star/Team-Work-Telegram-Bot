package skypro.TeamWorkTelegramBot.service.restApiServices;

import skypro.TeamWorkTelegramBot.entity.Report;

import java.util.Collection;

/**
 *Интерфейс для животных
 */
public interface ReportService {
    /**
     * Добавляет животного в базу данных.
     * @param report животное для добавления.
     * @return Добавленное животное.
     */
    Report addAnimal(Report report);
    /**
     * метод который находит животного по указанному идентификатору.
     * @param id Идентификатор животного для поиска.
     * @return животное, найденное по указанному идентификатору
     */
    Report findAnimal(Integer id);
    /**
     * метод редактирования животного в базе данных.
     * @param report животноое для редактирования.
     * @return отредактированное животное.
     */
    Report editAnimal(Report report);
    /**
     * метод удаляет животного из базы данных.
     * @param id Идентификатор животного для поиска.
     * @return удаленное животное.
     */
    void removeAnimal(Integer id);
    /**
     * метод получить всех животных из базы данных.
     */
    Collection<Report> getAllAnimals();
}
