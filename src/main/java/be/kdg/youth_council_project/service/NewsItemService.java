package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NewsItemService {
    List<NewsItem> getNewsItemsByYouthCouncilId(long id);

    List<NewsItemComment> getCommentsOfNewsItem(NewsItem newsItem);
    List<NewsItemLike> getLikesOfNewsItem(NewsItem newsItem);

    boolean createNewsItemLike(NewsItemLike newsItemLike);

    @Transactional
    void removeNewsItemLike(long newsItemId, long userId, long youthCouncilId);

    boolean isLikedByUser(Long id, long userId);
}
