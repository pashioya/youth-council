package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionPointRepository extends JpaRepository<ActionPoint, Long> {


    public List<ActionPoint> findByYouthCouncil(YouthCouncil youthCouncil);


    @Query(value="SELECT image FROM action_point_image api WHERE api.action_point_id=?1", nativeQuery = true)
    public List<String> getImagesByActionPointId(long actionPointId);

}
