package be.kdg.youth_council_project.repository.action_point;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActionPointCommentRepository extends JpaRepository<ActionPointComment, Long> {
    List<ActionPointComment> findByActionPoint(ActionPoint actionPoint);

    @Query("SELECT c FROM ActionPointComment c WHERE c.author.id = ?1")
    List<ActionPointComment> findAllByAuthorId(long userId);
    void deleteActionPointCommentByActionPointId(long actionPointId);
}
