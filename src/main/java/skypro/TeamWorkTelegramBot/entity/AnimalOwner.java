package skypro.TeamWorkTelegramBot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class AnimalOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Long idChat;
    String contactInformation;
    Integer stage;
    Boolean dogLover;
    Boolean tookTheAnimal;
}
