package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionPointLikeRepository extends JpaRepository<ActionPointLike, Long> {
    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM action_point_like WHERE user_id=?1 AND action_point_id=?2)" +
            " THEN true ELSE false END", nativeQuery = true)
    public boolean existsByUserIdAndActionPointId(long userId, long actionPointId);

    public List<ActionPointLike> findByActionPointLikeId_ActionPoint(ActionPoint actionPoint);
}
