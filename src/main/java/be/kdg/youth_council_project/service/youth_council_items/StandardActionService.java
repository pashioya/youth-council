package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;

import java.util.List;

public interface StandardActionService {
    List<StandardAction> findAll();

    StandardAction getStandardActionById(Long standardActionId);
}
