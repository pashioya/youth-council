package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.NewsItemDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;

import java.util.List;

public interface NewsItemService {
    List<NewsItem> getNewsItemsByYouthCouncilId(long id);

    List<NewsItemComment> getCommentsOfNewsItem(NewsItem newsItem);

    List<NewsItemLike> getLikesOfNewsItem(NewsItem newsItem);

    NewsItemLike createNewsItemLike(NewsItemLike newsItemLike);

    NewsItem createNewsItem(NewsItem newsItem);

    void setYouthCouncilOfNewsItem(NewsItem newsItem, long tenantId);

    void setImageOfNewsItem(NewsItem createdNewsItem, byte[] image);

    void setAuthorOfIdea(NewsItem createdNewsItem, long userId, long tenantId);

    void removeNewsItemLike(long newsItemId, long userId, long youthCouncilId);

    boolean isLikedByUser(Long id, long userId);

    void setUserOfNewsItemLike(NewsItemLike createdNewsItemLike, long userId, long tenantId);

    void setNewsItemOfNewsItemLike(NewsItemLike createdNewsItemLike, long newsItemId, long tenantId);

    void deleteNewsItem(long newsItemId);

    List<NewsItemComment> getCommentsByUserId(long userId);

    List<NewsItemComment> getAllCommentsByYouthCouncilId(long tenantId);

    NewsItemDto mapToDto(NewsItem newsItem);

    List<NewsItemComment> getAllComments();
}
