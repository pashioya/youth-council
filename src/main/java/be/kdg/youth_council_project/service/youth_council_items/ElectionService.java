package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;

import java.util.List;

public interface ElectionService {

    List<Election> getAllElectionsByYouthCouncilId(long youthCouncilId);
}
