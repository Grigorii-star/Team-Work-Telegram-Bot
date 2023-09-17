package skypro.TeamWorkTelegramBot.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс модели Volunteer
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
    private Boolean isBusy;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
