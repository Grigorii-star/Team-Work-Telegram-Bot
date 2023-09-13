package skypro.TeamWorkTelegramBot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnimalOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long idChat;
    private Boolean registered;
    private String contactInformation;
    private Boolean dogLover;
    private Boolean tookTheAnimal;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalOwner that = (AnimalOwner) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AnimalOwner{" +
                "id=" + id +
                ", idChat=" + idChat +
                ", registered=" + registered +
                ", contactInformation='" + contactInformation + '\'' +
                ", dogLover=" + dogLover +
                ", tookTheAnimal=" + tookTheAnimal +
                '}';
    }
}
