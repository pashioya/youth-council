package be.kdg.youth_council_project.repository.news_item;

import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsItemCommentRepository extends JpaRepository<NewsItemComment, Long> {
    List<NewsItemComment> findByNewsItem(NewsItem newsItem);

    @Query("SELECT c FROM NewsItemComment c WHERE c.author.id = ?1")
    List<NewsItemComment> findAllByUserId(long userId);

    @Query("SELECT c FROM NewsItemComment c WHERE c.newsItem.youthCouncil.id = ?1")
    List<NewsItemComment> findAllByYouthCouncilId(long tenantId);
}
