package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPointStatus;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.youth_council_items.ActionPointService;
import be.kdg.youth_council_project.service.youth_council_items.StandardActionService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final StandardActionService standardActionService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView getAllActionPoints(@TenantId long tenantId, @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("ActionPointControllerMVC is running getAllActionPoints");
        ModelAndView modelAndView = new ModelAndView("modules/action-points");
        List<ActionPoint> actionPoints = actionPointService.getActionPointsByYouthCouncilId(tenantId);
        List<ActionPointViewModel> actionPointViewModels = actionPointService.mapToViewModels(actionPoints,
                user, tenantId);
        modelAndView.addObject("standardActions", standardActionService.getAllStandardActions());
        modelAndView.addObject("statuses", ActionPointStatus.values());
        modelAndView.addObject("actionPoints", actionPointViewModels);
        return modelAndView;
    }
}
