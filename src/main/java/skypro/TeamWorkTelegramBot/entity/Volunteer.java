package skypro.TeamWorkTelegramBot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Volunteer {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long idChat;
    private String name;
//    @ManyToOne
//    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
