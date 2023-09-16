package skypro.TeamWorkTelegramBot.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Класс модели Animal
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private String report;
    private String telegramFieldId;
    private Integer fileSize;
    @OneToOne
    private BinaryContent binaryContent;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @ManyToOne
    @JoinColumn(name = "animal_owner_id")
    private AnimalOwner animalOwner;
}
