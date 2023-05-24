package be.kdg.youth_council_project.repository.action_point;

import be.kdg.youth_council_project.domain.platform.youth_council_items.images.ActionPointImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionPointImageRepository extends JpaRepository<ActionPointImage, Long> {
    List<ActionPointImage> findByActionPoint_Id(long actionPointId);
}
