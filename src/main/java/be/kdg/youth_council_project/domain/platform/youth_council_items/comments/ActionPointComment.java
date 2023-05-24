package be.kdg.youth_council_project.domain.platform.youth_council_items.comments;


import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ActionPointComment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;
    private String content;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="action_point_id")
    private ActionPoint actionPoint;

    private LocalDateTime createdDate;
}
