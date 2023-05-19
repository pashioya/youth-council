package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.mvc.viewmodels.StandardActionViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;

import java.util.List;

public interface StandardActionService {
    List<StandardAction> getAllStandardActions();

    StandardAction getStandardActionById(Long standardActionId);

    List<StandardAction> getStandardActionsByThemeId(Long themeId);

    void deleteStandardAction(long standardActionId);

    void updateStandardAction(long standardActionId, String standardActionName);

    StandardAction createStandardAction(long themId, String name);

    StandardActionViewModel mapToViewModel(StandardAction standardAction);
}
