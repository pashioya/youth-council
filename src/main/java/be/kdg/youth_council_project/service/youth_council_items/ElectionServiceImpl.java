package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;
import be.kdg.youth_council_project.repository.ElectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElectionServiceImpl implements ElectionService {

    private final ElectionRepository electionRepository;

    @Override
    public List<Election> getAllElectionsByYouthCouncilId(long youthCouncilId) {
        return electionRepository.getAllElectionsByYouthCouncilId(youthCouncilId);
    }
}
