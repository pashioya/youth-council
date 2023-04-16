package be.kdg.youth_council_project.repository.webpage;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.webpage.InformativePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformativePageRepository extends JpaRepository<InformativePage, Long> {

    public void findByYouthCouncilAndUrl(YouthCouncil youthCouncil, String url);
}
