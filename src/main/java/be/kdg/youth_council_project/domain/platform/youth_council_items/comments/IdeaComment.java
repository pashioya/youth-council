package be.kdg.youth_council_project.domain.platform.youth_council_items.comments;

import be.kdg.youth_council_project.domain.platform.User;
import javax.persistence.*;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class IdeaComment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;
    private String content;


    @ManyToOne
    @JoinColumn(name="idea_id")
    private Idea idea;


    private LocalDateTime createdDate;

}
