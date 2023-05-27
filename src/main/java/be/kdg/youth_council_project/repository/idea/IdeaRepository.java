package be.kdg.youth_council_project.repository.idea;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByYouthCouncil(YouthCouncil youthCouncil);

    @Query(value = "SELECT * FROM idea i WHERE i.id =?1 AND i.youth_council_id =?2", nativeQuery = true)
    Optional<Idea> findByIdAndYouthCouncilId(long ideaId, long youthCouncilId);

    List<Idea> findByYouthCouncilAndAuthor(YouthCouncil youthCouncil, User user);

    @Modifying
    @Query(value = "DELETE FROM action_points_linked_ideas apli WHERE apli.IDEA_ID=?1", nativeQuery = true)
    void deleteActionPointLinksById(long ideaId);

    @Query(value = "SELECT * FROM idea i WHERE i.author_id =?1", nativeQuery = true)
    List<Idea> findByAuthor(long userId);

    @Query(value = "SELECT * FROM idea i WHERE i.id IN (SELECT idea_id FROM action_points_linked_ideas WHERE action_point_id =?1)", nativeQuery = true)
    List<Idea> findAllByActionPointId(Long id);

    @Query(value = "SELECT * FROM idea i WHERE i.youth_council_id =?1", nativeQuery = true)
    List<Idea> findAllByYouthCouncilId(long tenantId);

    @Query(value = "SELECT * FROM idea i WHERE i.theme_id =?1", nativeQuery = true)
    List<Idea> findAllByThemeId(Long themeId);
}
