package be.kdg.youth_council_project.repository.action_point;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionPointLikeRepository extends JpaRepository<ActionPointLike, Long> {
    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM action_point_like WHERE user_id=?1 AND action_point_id=?2)" +
            " THEN true ELSE false END", nativeQuery = true)
    boolean existsByUserIdAndActionPointId(long userId, long actionPointId);

    List<ActionPointLike> findById_ActionPoint(ActionPoint actionPoint);


    @Query(value = "SELECT * FROM action_point_like WHERE action_point_id=?1 AND user_id=?2", nativeQuery = true)
    Optional<ActionPointLike> findByActionPointIdAndUserId(long actionPointId, long userId);

    @Query(value = "SELECT * FROM action_point_like apl JOIN action_point ap ON (apl.action_point_id=ap.id) WHERE apl.action_point_id=?1 AND apl.user_id=?2 AND ap.youth_council_id=?3", nativeQuery = true)
    Optional<ActionPointLike> findByActionPointIdAndUserIdAndYouthCouncilId(long actionPointId, long userId, long youthCouncilId);

    @Query(value = "SELECT COUNT(*) FROM action_point_like WHERE action_point_id=?1", nativeQuery = true)
    long countByActionPointId(Long id);
}
