package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StandardActionRepository extends JpaRepository<StandardAction, Long> {

    @Query("select s from StandardAction s where s.theme.id = ?1")
    List<StandardAction> findAllByThemeId(Long themeId);
}
