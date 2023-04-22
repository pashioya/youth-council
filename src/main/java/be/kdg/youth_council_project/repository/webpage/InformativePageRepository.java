package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.webpage.WebPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativePageRepository extends JpaRepository<WebPage, Long> {

}
