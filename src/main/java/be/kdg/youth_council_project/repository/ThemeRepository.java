package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
