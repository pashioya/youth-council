package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YouthCouncilRepository extends JpaRepository<YouthCouncil, Integer> {
}
