package be.kdg.youth_council_project.domain.platform.youth_council_items.like;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IdeaLike {

    @EmbeddedId
    private IdeaLikeId ideaLikeId;

    private LocalDateTime likedDateTime;
}
