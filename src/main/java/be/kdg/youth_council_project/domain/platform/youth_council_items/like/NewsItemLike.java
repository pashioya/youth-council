package be.kdg.youth_council_project.domain.platform.youth_council_items.like;

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
public class NewsItemLike {
    @EmbeddedId
    private NewsItemLikeId id;

    private LocalDateTime likedDateTime;
}
