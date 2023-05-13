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

    // should I do LEFT JOIN FETCH for images (loading them immediately)
    List<Idea> findByYouthCouncil(YouthCouncil youthCouncil);

    @Query(value = "SELECT * FROM idea i WHERE i.id =?1 AND i.youth_council_id =?2", nativeQuery = true)
    Optional<Idea> findByIdAndYouthCouncilId(long ideaId, long youthCouncilId);

    List<Idea> findByYouthCouncilAndAuthor(YouthCouncil youthCouncil, User user);


    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM idea i WHERE i.id =?1 AND i.youth_council_id =?2", nativeQuery = true)
    boolean ideaBelongsToYouthCouncil(long ideaId, long youthCouncilId);


    @Modifying
    @Query(value = "DELETE FROM action_points_linked_ideas apli WHERE apli.IDEA_ID=?1", nativeQuery = true)
    void deleteActionPointLinksById(long ideaId);

    @Query(value = "SELECT * FROM idea i WHERE i.author_id =?1", nativeQuery = true)
    List<Idea> findByAuthor(long userId);

    List<Idea> getIdeasByAuthorId(long authorId);

    void deleteIdeaByAuthorId(long authorId);
    @Query(value = "SELECT FIRST_NAME, LAST_NAME, USERNAME, POST_CODE FROM idea i JOIN app_user a ON i.author_id=a.user_id WHERE i.author_id=?", nativeQuery = true)
    User getUserByAuthorId(long authorId);
}
