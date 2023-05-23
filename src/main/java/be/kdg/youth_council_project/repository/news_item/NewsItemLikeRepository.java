package be.kdg.youth_council_project.repository.news_item;

import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsItemLikeRepository extends JpaRepository<NewsItemLike, Long> {
    List<NewsItemLike> findById_NewsItem(NewsItem newsItem);

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM news_item_like WHERE user_id=?1 AND news_item_id=?2) THEN " +
            "true " +
            "ELSE false END", nativeQuery = true)
    boolean existsByUserIdAndNewsItemId(Long userId, Long newsItemId);

    @Query(value = "SELECT * FROM news_item_like il JOIN news_item i ON (i.id=il.news_item_id) WHERE il.news_item_id=?1 " +
            "AND il.user_id=?2 " +
            "AND i.youth_council_id=?3", nativeQuery = true)
    Optional<NewsItemLike> findByNewsItemIdAndUserIdAndYouthCouncilId(long newsItemId, long userId, long youthCouncilId);
}
