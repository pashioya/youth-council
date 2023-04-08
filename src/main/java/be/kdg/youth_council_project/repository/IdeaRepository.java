package be.kdg.youth_council_project.repository;

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
    public List<Idea> findByYouthCouncil(YouthCouncil youthCouncil);

    @Query(value = "SELECT * FROM idea i WHERE i.id =?1 AND i.youth_council_id =?2", nativeQuery = true)
    public Optional<Idea> findByIdAndYouthCouncilId(long ideaId, long youthCouncilId);
    public List<Idea> findByYouthCouncilAndAuthor(YouthCouncil youthCouncil, User user);


    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM idea i WHERE i.id =?1 AND i.youth_council_id =?2", nativeQuery = true)
    public boolean ideaBelongsToYouthCouncil(long ideaId, long youthCouncilId);


    @Query(value="SELECT image FROM idea_image ii WHERE ii.IDEA_ID=?1", nativeQuery = true)
    public List<String> getImagesById(long ideaId);

    @Modifying
    @Query(value="DELETE FROM action_points_linked_ideas apli WHERE apli.IDEA_ID=?1", nativeQuery = true)
    public void deleteActionPointLinksById(long ideaId);
}
