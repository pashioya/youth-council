package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.mvc.viewmodels.StandardActionViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import be.kdg.youth_council_project.repository.StandardActionRepository;
import be.kdg.youth_council_project.repository.ThemeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StandardActionServiceImpl implements StandardActionService {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final StandardActionRepository standardActionRepository;
    private final ThemeRepository themeRepository;

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

    @Override
    public void deleteStandardAction(long standardActionId) {
        LOGGER.info("StandardActionService: deleteStandardAction with id {}", standardActionId);
        standardActionRepository.deleteById(standardActionId);
    }

    @Override
    public void updateStandardAction(long standardActionId, String standardActionName) {
        LOGGER.info("StandardActionService: updateStandardAction with id {}", standardActionId);
        StandardAction standardAction = standardActionRepository.findById(standardActionId).orElse(null);
        if (standardAction != null) {
            standardAction.setName(standardActionName);
            standardActionRepository.save(standardAction);
        }
       else {
            LOGGER.error("StandardActionService: updateStandardAction with id {} failed", standardActionId);
        }
    }

    @Override
    public StandardAction createStandardAction(long themId, String name) {
        LOGGER.info("StandardActionService: createStandardAction with name {}", name);
        StandardAction standardAction = new StandardAction();
        themeRepository.findById(themId).ifPresent(standardAction::setTheme);
        standardAction.setName(name);
        return standardActionRepository.save(standardAction);
    }

    @Override
    public StandardActionViewModel mapToViewModel(StandardAction standardAction) {
        LOGGER.info("StandardActionService: mapToViewModel with id {}", standardAction.getId());
        return new StandardActionViewModel(
                standardAction.getId(),
                standardAction.getName(),
                standardAction.getTheme().getName()
        );
    }
}
