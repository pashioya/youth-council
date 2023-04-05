package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionPointRepository extends JpaRepository<ActionPoint, Long> {


    public List<ActionPoint> findByYouthCouncil(YouthCouncil youthCouncil);


    @Query(value="SELECT image FROM action_point_image api WHERE api.action_point_id=?1", nativeQuery = true)
    public List<String> getImagesByActionPointId(long actionPointId);


    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM action_point a WHERE a.id =?1 AND a.youth_council_id =?2", nativeQuery = true)
    boolean actionPointBelongsToYouthCouncil(long actionPointId, long youthCouncilId);
}
