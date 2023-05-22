package be.kdg.youth_council_project.repository.news_item;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    List<NewsItem> findAllByYouthCouncilId(long id);

    Optional<NewsItem> findByIdAndYouthCouncilId(long id, long youthCouncilId);

    void deleteByAuthorId(long authorId);
}
