package skypro.TeamWorkTelegramBot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Сущность приюта для животных.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private Collection<Report> reports;
    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private Collection<AnimalOwner> animalOwners;
    @OneToMany(mappedBy = "shelter")
    @JsonIgnore
    private Collection<Volunteer> volunteers;
}
