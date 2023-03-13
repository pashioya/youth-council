package be.kdg.youth_council_project.domain.platform.youthCouncilItems;


import be.kdg.youth_council_project.domain.platform.User;
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
    private User user;
    private String content;


    @ManyToOne
    @JoinColumn(name="idea_id")
    private Idea ideaCommentedOn;

    private LocalDateTime createdDate;
}
