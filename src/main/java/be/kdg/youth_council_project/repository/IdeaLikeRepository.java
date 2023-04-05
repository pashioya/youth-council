package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaLikeRepository extends JpaRepository<IdeaLike, Long> {

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM idea_like WHERE user_id=?1 AND idea_id=?2) THEN true ELSE false END", nativeQuery = true)
    public boolean existsByUserIdAndIdeaId(long userId, long ideaId);

    long countAllByIdeaLikeId_Idea(Idea idea);
}
