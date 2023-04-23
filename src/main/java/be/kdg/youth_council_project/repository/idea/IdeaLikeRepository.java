package be.kdg.youth_council_project.repository.idea;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaLikeRepository extends JpaRepository<IdeaLike, Long> {

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM idea_like WHERE user_id=?1 AND idea_id=?2) THEN true ELSE false END", nativeQuery = true)
    boolean existsByUserIdAndIdeaId(long userId, long ideaId);

    List<IdeaLike> findById_Idea(Idea idea);


    @Query(value = "SELECT * FROM idea_like WHERE idea_id=?1 AND user_id=?2", nativeQuery = true)
    Optional<IdeaLike> findByIdeaIdAndUserId(long ideaId, long userId);

    @Query(value = "SELECT * FROM idea_like il JOIN idea i ON (i.id=il.idea_id) WHERE il.idea_id=?1 AND il.user_id=?2 AND i.youth_council_id", nativeQuery = true)
    Optional<IdeaLike> findByIdeaIdAndUserIdAndYouthCouncilId(long actionPointId, long userId, long youthCouncilId);
}
