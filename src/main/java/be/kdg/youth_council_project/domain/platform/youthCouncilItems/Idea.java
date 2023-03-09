package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name="action_point_id")
    private ActionPoint actionPoint;
    @Transient
    private List<String> images;
    private LocalDateTime dateAdded;
    private long likes;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

}
