package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import lombok.*;

import javax.persistence.*;
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
    @Transient
    private List<String> image;
    private LocalDateTime dateAdded;
    private long likes;
    @OneToMany(mappedBy="actionPoint")
    private List<Idea> linkedIdeas;
    @ManyToOne
    @JoinColumn(name="standardaction_id")
    private StandardAction linkedStandardAction;
    @OneToMany
    @JoinColumn(name="column_id")
    private List<Comment> comments;
    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

}
