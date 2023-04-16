package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;
import be.kdg.youth_council_project.domain.webpage.HomePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomePageRepository extends JpaRepository<HomePage, Long> {



    @Query(value="SELECT * from home_page h " +
            "JOIN web_page w on h.id=w.id " +
            "WHERE h.youth_council_id = ?1", nativeQuery = true)
    Optional<HomePage> findByYouthCouncilId(long youthCouncilId);
}
