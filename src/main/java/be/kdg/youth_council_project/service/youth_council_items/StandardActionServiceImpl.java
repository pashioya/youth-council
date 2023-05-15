package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import be.kdg.youth_council_project.repository.StandardActionRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StandardActionServiceImpl implements StandardActionService {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final StandardActionRepository standardActionRepository;

    @Override
    public List<StandardAction> getAllStandardActions() {
        return standardActionRepository.findAll();
    }

    @Override
    public StandardAction getStandardActionById(Long standardActionId) {
        LOGGER.info("StandardActionService: getStandardActionById with id {}", standardActionId);
        return standardActionRepository.findById(standardActionId).orElse(null);
    }

    @Override
    public List<StandardAction> getStandardActionsByThemeId(Long themeId) {
        LOGGER.info("StandardActionService: getStandardActionsByThemeId with id {}", themeId);
        return standardActionRepository.findAllByThemeId(themeId);
    }
}
