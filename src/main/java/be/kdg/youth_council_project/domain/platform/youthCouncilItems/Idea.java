package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.User;
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
public class Idea {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name="action_point_id")
    private ActionPoint actionPoint;

    @ManyToOne
    @JoinColumn(name="theme_id")
    private Theme theme;
    @ElementCollection
    @CollectionTable(name="image", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="image")
    private List<String> images;
    private LocalDateTime dateAdded;
    private long likes;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

    public Idea(String description, List<String> images) {
        this.description = description;
        this.images = images;
        this.likes=0;
        this.dateAdded=LocalDateTime.now();
    }
}
