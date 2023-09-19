package skypro.TeamWorkTelegramBot.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность пользователя Telegram бота.
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnimalOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long idChat;
    private String contactInformation;
    /**
     * Позволяет определить, что пользователь зарегистрирован в базе.
     */
    private Boolean registered;
    /**
     * Позволяет определить какой приют интересует пользователя.
     */
    private Boolean dogLover;
    /**
     * Позволяет определить, что пользователь уже взял животное.
     */
    private Boolean tookTheAnimal;
    /**
     * Проверка для возможности сохранить контакт.
     */
    private Boolean canSaveContact;
    /**
     * Позволяет определить, является ли пользователь волонтером.
     */
    private Boolean isVolunteer;
    /**
     * Проверка для возможности связаться с волонтером.
     */
    private Boolean helpVolunteer;
    /**
     * Проверка для возможности отправить отчет о животном.
     */
    private Boolean canSendReport;
    /**
     * Проверка для возможности определить в чате ли пользователь.
     */
    private Boolean inChat;
    @OneToOne
    private Volunteer volunteer;
    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;
}
