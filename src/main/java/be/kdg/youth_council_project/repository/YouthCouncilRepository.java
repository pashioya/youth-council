package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface YouthCouncilRepository extends JpaRepository<YouthCouncil, Long> {
    Optional<YouthCouncil> findBySlug(String slug);

}
