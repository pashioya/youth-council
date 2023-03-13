package be.kdg.youth_council_project.domain.platform.youthCouncilItems.like;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IdeaLikeId implements Serializable {

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User likedBy;

    @ManyToOne
    @JoinColumn(name="idea_id", nullable=false)
    private Idea idea;
}
