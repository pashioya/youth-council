package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.webpage.InformativePageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformativePageTemplateRepository extends JpaRepository<InformativePageTemplate, Long> {

    @Query(value="SELECT DISTINCT i FROM InformativePageTemplate i LEFT JOIN FETCH i.sections s")
    List<InformativePageTemplate> findAllWithSections();
}
