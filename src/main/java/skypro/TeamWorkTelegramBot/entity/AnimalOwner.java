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

    public AnimalOwner(Integer id,
                       Long idChat,
                       String contactInformation,
                       Boolean registered,
                       Boolean dogLover,
                       Boolean tookTheAnimal,
                       Boolean canSaveContact,
                       Boolean beVolunteer,
                       Boolean helpVolunteer,
                       Boolean canSendReport) {
        this.id = id;
        this.idChat = idChat;
        this.contactInformation = contactInformation;
        this.registered = registered;
        this.dogLover = dogLover;
        this.tookTheAnimal = tookTheAnimal;
        this.canSaveContact = canSaveContact;
        this.beVolunteer = beVolunteer;
        this.helpVolunteer = helpVolunteer;
        this.canSendReport = canSendReport;
    }

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
    @OneToOne
    private Volunteer volunteer;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
