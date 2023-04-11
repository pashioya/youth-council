package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsItemLikeRepository extends JpaRepository<NewsItemLike, Long> {
    List<NewsItemLike> findByNewsItemLikeId_NewsItem(NewsItem newsItem);
}
