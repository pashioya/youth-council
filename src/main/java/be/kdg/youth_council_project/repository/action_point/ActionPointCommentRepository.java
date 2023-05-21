package be.kdg.youth_council_project.repository.action_point;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActionPointCommentRepository extends JpaRepository<ActionPointComment, Long> {
    List<ActionPointComment> findByActionPoint(ActionPoint actionPoint);

    @Query("SELECT c FROM ActionPointComment c WHERE c.author.id = ?1")
    List<ActionPointComment> findAllByAuthorId(long userId);

    @Query("SELECT c FROM ActionPointComment c WHERE c.actionPoint.youthCouncil.id = ?1")
    List<ActionPointComment> findAllByYouthCouncilId(long tenantId);

    @Query(value = "SELECT * FROM action_point_comment apc JOIN action_point ap ON (ap.id=apc.action_point_id) WHERE apc.id=?1 AND ap.id=?2", nativeQuery = true)
    Optional<ActionPointComment> findByIdAndActionPointId(long actionPointId, long commentId);
}
