package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.comments.ActionPointComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionPointCommentRepository extends JpaRepository<ActionPointComment, Long> {
}
