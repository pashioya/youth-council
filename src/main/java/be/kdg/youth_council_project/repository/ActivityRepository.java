package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
