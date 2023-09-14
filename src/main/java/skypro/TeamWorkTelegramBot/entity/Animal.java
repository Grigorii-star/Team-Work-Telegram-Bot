package skypro.TeamWorkTelegramBot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Animal {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String filePath;
    private long fileSize;
    private String mediaType;
    //@Lob
    private byte[] data;
//    @ManyToOne
//    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
//    @ManyToOne
//    @JoinColumn(name = "owner_id")
    private AnimalOwner animalOwner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
