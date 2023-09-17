package skypro.TeamWorkTelegramBot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

/**
 *Класс animalOwner, модель animalOwner
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnimalOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long idChat;
    private String contactInformation;
    private Boolean registered;
    private Boolean dogLover;
    private Boolean tookTheAnimal;
    private Boolean canSaveContact;
    private Boolean beVolunteer;
    private Boolean helpVolunteer;
    private Boolean canSendReport;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
