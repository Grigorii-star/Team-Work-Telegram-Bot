package skypro.TeamWorkTelegramBot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AnimalOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long idChat;
    private String contactInformation;
    private Integer stage;
    private Boolean dogLover;
    private Boolean tookTheAnimal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Boolean getDogLover() {
        return dogLover;
    }

    public void setDogLover(Boolean dogLover) {
        this.dogLover = dogLover;
    }

    public Boolean getTookTheAnimal() {
        return tookTheAnimal;
    }

    public void setTookTheAnimal(Boolean tookTheAnimal) {
        this.tookTheAnimal = tookTheAnimal;
    }

    public AnimalOwner(Integer id, Long idChat, String contactInformation, Integer stage, Boolean dogLover, Boolean tookTheAnimal) {
        this.id = id;
        this.idChat = idChat;
        this.contactInformation = contactInformation;
        this.stage = stage;
        this.dogLover = dogLover;
        this.tookTheAnimal = tookTheAnimal;
    }

    public AnimalOwner() {
    }

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
                ", contactInformation='" + contactInformation + '\'' +
                ", stage=" + stage +
                ", dogLover=" + dogLover +
                ", tookTheAnimal=" + tookTheAnimal +
                '}';
    }
}
