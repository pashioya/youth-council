package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StandardActionRepository extends JpaRepository<StandardAction, Long> {
}
