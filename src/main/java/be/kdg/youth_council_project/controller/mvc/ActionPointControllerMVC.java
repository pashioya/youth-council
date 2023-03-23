package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActionPointViewModel;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.service.ActionPointService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/youth-councils/{id}/action-points")
@AllArgsConstructor
public class ActionPointControllerMVC {
    private final ActionPointService actionPointService;
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView getAllActionPoints(@PathVariable("id") long id) {
        LOGGER.info("ActionPointControllerMVC is running getAllActionPoints");
        ModelAndView modelAndView = new ModelAndView("action-points");
        List<ActionPoint> actionPoints = actionPointService.getActionPointsByYouthCouncilId(id);
        List<ActionPointViewModel> actionPointViewModels = actionPoints.stream().map(actionPointService::mapActionPointToActionPointViewModel).toList();
        modelAndView.addObject("actionPoints", actionPointViewModels);
        return modelAndView;
    }
    @GetMapping("/submit")
    public ModelAndView getSubmitActionPoint(@PathVariable("id") long id) {
        LOGGER.info("ActionPointControllerMVC is running getSubmitActionPoint");
        return new ModelAndView("forms/submit-action-point");
    }
}
