package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
}