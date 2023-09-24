package skypro.TeamWorkTelegramBot.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность содержит бинарный код.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BinaryContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Lob
    @Column(columnDefinition = "oid")
    private byte[] data;
}
