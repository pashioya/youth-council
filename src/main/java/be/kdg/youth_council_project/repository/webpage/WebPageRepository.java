package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.webpage.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Long> {

    @Query(value="SELECT w FROM WebPage w LEFT JOIN FETCH w.sections s WHERE w.youthCouncil.id = ?1")
    Optional<WebPage> findByYouthCouncilId(long youthCouncilId);
}
