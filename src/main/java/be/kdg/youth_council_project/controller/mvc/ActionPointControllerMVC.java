package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.service.ActionPointService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/action-points")
@AllArgsConstructor
public class ActionPointControllerMVC {
    private final ActionPointService actionPointService;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView getAllActionPoints(@TenantId long tenantId) {
        LOGGER.info("ActionPointControllerMVC is running getAllActionPoints");
        ModelAndView modelAndView = new ModelAndView("action-points");
        List<ActionPoint> actionPoints = actionPointService.getActionPointsByYouthCouncilId(tenantId);
        List<ActionPointViewModel> actionPointViewModels = actionPoints.stream().map(actionPointService::mapActionPointToActionPointViewModel).toList();
        modelAndView.addObject("actionPoints", actionPointViewModels);
        return modelAndView;
    }
}
