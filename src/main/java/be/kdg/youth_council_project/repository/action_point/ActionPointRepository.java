package be.kdg.youth_council_project.repository.action_point;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.ActionPointImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionPointRepository extends JpaRepository<ActionPoint, Long> {

    List<ActionPoint> findByYouthCouncil(YouthCouncil youthCouncil);

    @Query(value = "SELECT image FROM action_point_image api WHERE api.action_point_id=?1", nativeQuery = true)
    List<ActionPointImage> getImagesByActionPointId(long actionPointId);

    Optional<ActionPoint> findActionPointByIdAndYouthCouncil_Id(long actionPointId, long youthCouncilId);
}
