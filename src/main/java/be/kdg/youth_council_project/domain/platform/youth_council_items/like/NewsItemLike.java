package be.kdg.youth_council_project.domain.platform.youth_council_items.like;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
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
public class NewsItemLike {
    @EmbeddedId
    private NewsItemLikeId id;

    private LocalDateTime likedDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "newsItem_id", insertable = false, updatable = false)
    private NewsItem newsItem;

    public NewsItemLike(NewsItemLikeId id, LocalDateTime likedDateTime) {
        this.id = id;
        this.likedDateTime = likedDateTime;
    }
}
