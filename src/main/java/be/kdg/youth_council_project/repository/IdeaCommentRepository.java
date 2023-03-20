package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.comments.IdeaComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {
    public List<IdeaComment> findByIdea(Idea idea);
}
