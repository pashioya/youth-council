package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.webpage.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebPageRepository extends JpaRepository<WebPage, Long> {

    @Query(value="SELECT w FROM WebPage w LEFT JOIN FETCH w.sections s WHERE w.youthCouncil.id = ?1")
    Optional<List<WebPage>> findAllByYouthCouncilId(long youthCouncilId);

    @Query(value="SELECT w FROM WebPage w LEFT JOIN FETCH w.sections s WHERE w.youthCouncil.id = ?1 AND w.isHomepage " +
            "= " +
            "true")
    Optional<WebPage> findYouthCouncilHomePage(long youthCouncilId);


    @Query(value="SELECT w FROM WebPage w LEFT JOIN FETCH w.sections s WHERE w.id = ?1")
    Optional<WebPage> findById(long id);

    @Query(value="SELECT w FROM WebPage w LEFT JOIN FETCH w.sections s WHERE s.id = ?1")
    Optional<WebPage> findBySectionId(long sectionId);

    Optional<WebPage> findByIdAndYouthCouncilId(long webPageId, long youthCouncilId);

    @Query(value="SELECT w FROM WebPage w LEFT JOIN FETCH w.sections s WHERE w.youthCouncil.id = ?1 AND w.isHomepage " +
            "= " +
            "false")
    Optional<List<WebPage>> findALlInformativePagesByYouthCouncilId(long tenantId);
}
