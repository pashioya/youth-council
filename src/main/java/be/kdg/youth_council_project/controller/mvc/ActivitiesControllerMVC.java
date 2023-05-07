package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ActivityViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;
import be.kdg.youth_council_project.service.youth_council_items.ActivityService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/activities")
public class ActivitiesControllerMVC {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ModelAndView getActivities(@TenantId long tenantId) {
        LOGGER.info("ActivitiesControllerMVC is running getActivities");
        List<Activity> activities = activityService.getActivitiesByYouthCouncilId(tenantId);
        List<ActivityViewModel> activityViewModels = activities.stream().map(activity -> modelMapper.map(activity, ActivityViewModel.class)).toList();
        return new ModelAndView("modules/activities", "activities", activityViewModels);
    }
}
