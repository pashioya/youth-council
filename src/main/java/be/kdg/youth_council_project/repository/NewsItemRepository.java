package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsItemRepository extends JpaRepository<NewsItem, Long> {
    List<NewsItem> findAllByYouthCouncilId(long id);
}
