package be.kdg.youth_council_project.domain.platform.youth_council_items.like;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
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
public class ActionPointLikeId implements Serializable {

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User likedBy;

    @ManyToOne
    @JoinColumn(name="action_point_id", nullable=false)
    private ActionPoint actionPoint;
}
