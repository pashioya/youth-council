package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    Optional<Municipality> findByNameIgnoreCase(String municipalityName);

    @Query(value = "SELECT * FROM municipality m JOIN youth_council y ON m.id = y.municipality_id WHERE y.id =?1", nativeQuery = true)
    Optional<Municipality> getMunicipalityOfYouthCouncilByYouthCouncilId(long youthCouncilId);

    @Query(value = "SELECT * FROM municipality m JOIN youth_council y ON m.id = y.municipality_id WHERE y.id =?1", nativeQuery = true)
    Optional<Municipality> getMunicipalityByYouthCouncilId(long tenantId);
}