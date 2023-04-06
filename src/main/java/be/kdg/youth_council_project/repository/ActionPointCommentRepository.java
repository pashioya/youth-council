package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActionPointCommentRepository extends JpaRepository<ActionPointComment, Long> {
    public List<ActionPointComment> findByActionPoint(ActionPoint actionPoint);
}
