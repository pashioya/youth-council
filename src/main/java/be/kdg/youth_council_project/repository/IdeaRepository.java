package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    @Query(value="SELECT i FROM Idea i LEFT JOIN FETCH i.images WHERE i.youthCouncil= ?1")
    public List<Idea> findByYouthCouncil(YouthCouncil youthCouncil);


    @Query(value="SELECT i FROM Idea i LEFT JOIN FETCH i.images WHERE i.youthCouncil= ?1 AND i.author = ?2")
    public List<Idea> findByYouthCouncilAndUser(YouthCouncil youthCouncil, User user);


    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM idea i WHERE i.idea_id =?1 AND i.youth_council_id =?2", nativeQuery = true)
    public boolean ideaBelongsToYouthCouncil(long ideaId, long youthCouncilId);

}
