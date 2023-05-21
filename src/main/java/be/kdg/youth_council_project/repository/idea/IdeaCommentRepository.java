package be.kdg.youth_council_project.repository.idea;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IdeaCommentRepository extends JpaRepository<IdeaComment, Long> {

    List<IdeaComment> findByIdea(Idea idea);

    @Query("SELECT c FROM IdeaComment c WHERE c.author.id = ?1")
    List<IdeaComment> findByAuthorId(long userId);

    @Query("SELECT c FROM IdeaComment c WHERE c.idea.youthCouncil.id = ?1")
    List<IdeaComment> findAllByYouthCouncilId(long tenantId);

    @Query(value = "SELECT * FROM idea_comment ic JOIN idea i ON (i.id=ic.idea_id) WHERE ic.id=?1 AND i.id=?2", nativeQuery = true)
    Optional<IdeaComment> findByIdAndIdeaIdAndAndYouthCouncilId(long commentId, long ideaId);
}
