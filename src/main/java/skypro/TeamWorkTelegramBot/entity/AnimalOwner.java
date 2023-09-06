package skypro.TeamWorkTelegramBot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class AnimalOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    Long idChat;
    Integer stage;
    Boolean dogLover;
    Boolean tookTheAnimal;
    String contactInformation;
}
