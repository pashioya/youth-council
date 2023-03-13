package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActionPoint {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private ActionPointStatus status;
    private String title;
    private String video;
    private String description;

    @ElementCollection
    @CollectionTable(name="action_point_image", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="image")
    private List<String> images;
    private LocalDateTime createdDate;
    private long likes;
    @OneToMany(mappedBy="actionPoint")
    private List<Idea> linkedIdeas;
    @ManyToOne
    @JoinColumn(name="standardaction_id")
    private StandardAction linkedStandardAction;



    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

}
