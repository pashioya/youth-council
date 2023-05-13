package be.kdg.youth_council_project.repository.idea;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {

    List<IdeaComment> findByIdea(Idea idea);

    @Query("SELECT c FROM IdeaComment c WHERE c.author.id = ?1")
    List<IdeaComment> findByAuthorId(long userId);
}
