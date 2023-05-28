package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.ElectionDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;

import java.time.LocalDateTime;
import java.util.List;

public interface ElectionService {

    List<Election> getAllElectionsByYouthCouncilId(long youthCouncilId);

    Election createElection(ElectionDto electionDto, long tenantId);

    ElectionDto mapToDto(Election election);

    LocalDateTime parseElectionDate(String date);

    void deleteElection(long id, long tenantId);

    Election getElectionById(long electionId, long tenantId);

    Election updateElection(ElectionDto electionDto, long tenantId);
}
