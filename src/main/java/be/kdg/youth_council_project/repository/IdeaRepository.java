package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
