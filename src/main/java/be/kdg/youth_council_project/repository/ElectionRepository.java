package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ElectionRepository extends JpaRepository<Election, Long> {

    @Query(value = "SELECT * FROM election e JOIN youth_council y ON e.youth_council_id = y.id WHERE y.id =?1", nativeQuery = true)
    List<Election> getAllElectionsByYouthCouncilId(long youthCouncilId);

    Optional<Election> findByIdAndYouthCouncilId(long electionId, long youthCouncilId);
}
