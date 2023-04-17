package be.kdg.youth_council_project.repository.idea;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {

    public List<IdeaComment> findByIdea(Idea idea);
}
