package be.kdg.youth_council_project.domain.platform.youth_council_items.like;


import be.kdg.youth_council_project.domain.platform.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IdeaLike {

    @EmbeddedId
    private IdeaLikeId id;
    private LocalDateTime likedDateTime;

    public IdeaLike(IdeaLikeId id, LocalDateTime likedDateTime) {
        this.id = id;
        this.likedDateTime = likedDateTime;
    }

    @ManyToOne
    @JoinColumn(name="user_id", insertable = false, updatable = false)
    private User author;
}
