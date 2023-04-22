package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.webpage.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {


    Optional<Section> findByIdAndPageId(long sectionId, long pageId);
}
