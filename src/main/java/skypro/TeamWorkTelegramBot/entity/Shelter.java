package skypro.TeamWorkTelegramBot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

/**
 * Класс модели Shelter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Shelter {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
//    @OneToMany(mappedBy = "shelter")
//    @JsonIgnore
    private Collection<Shelter> shelters;
//    @OneToMany(mappedBy = "shelter")
//    @JsonIgnore
    private Collection<AnimalOwner> animalOwners;
//    @OneToMany(mappedBy = "shelter")
//    @JsonIgnore
    private Collection<Volunteer> volunteers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(id, shelter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
