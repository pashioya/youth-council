package be.kdg.youth_council_project.domain.platform.youth_council_items.like;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActionPointLike {

    @EmbeddedId
    private ActionPointLikeId id;

    private LocalDateTime likedDateTime;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="action_point_id", insertable = false, updatable = false)
    private ActionPoint actionPoint;

    public ActionPointLike(ActionPointLikeId id, LocalDateTime likedDateTime) {
        this.id = id;
        this.likedDateTime = likedDateTime;
    }
}
