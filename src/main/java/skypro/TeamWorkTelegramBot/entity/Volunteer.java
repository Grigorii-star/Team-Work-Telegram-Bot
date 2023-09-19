package skypro.TeamWorkTelegramBot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Сущность волонтера.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long idChat;
    private String name;
    /**
     * Позволяет проверить занят ли волонтр.
     */
    private Boolean isBusy;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
    @OneToOne
    private AnimalOwner animalOwner;
}
