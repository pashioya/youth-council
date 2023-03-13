package be.kdg.youth_council_project.domain.platform.youthCouncilItems.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActionPointLike {

    @EmbeddedId
    private ActionPointLikeId actionPointLikeId;

    private LocalDateTime likedDateTime;
}
