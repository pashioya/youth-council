package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.util.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebPageRepository extends JpaRepository<WebPage, Long> {
    WebPage findByYouthCouncilId(long youthCouncilId);
}
