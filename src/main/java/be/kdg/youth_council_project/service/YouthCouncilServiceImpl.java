package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import javax.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class YouthCouncilServiceImpl implements YouthCouncilService{
    private final YouthCouncilRepository youthCouncilRepository;
    @Override
    public YouthCouncil getYouthCouncilById(long id) {
        return youthCouncilRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
