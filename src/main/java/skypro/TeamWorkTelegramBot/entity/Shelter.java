package skypro.TeamWorkTelegramBot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Класс модели Shelter
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Shelter {

    public Shelter(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

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
