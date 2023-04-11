package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class YouthCouncilControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ModelAndView getYouthCouncil(@TenantId long tenantId){
        LOGGER.info("YouthCouncilControllerMVC is running getYouthCouncil with tenantId {}", tenantId);
        return new ModelAndView("youth-council");
    }
    @GetMapping("/elections")
    public ModelAndView getElections(@TenantId long tenantId){
        LOGGER.info("YouthCouncilControllerMVC is running getElections with tenantId {}", tenantId);
        return new ModelAndView("elections");
    }
    @GetMapping("/admin-dashboard")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getAdminDashboard(@TenantId long tenantId){
        LOGGER.info("YouthCouncilControllerMVC is running getAdminDashboard with tenantId {}", tenantId);
        return new ModelAndView("admin-dashboard");
    }
    @GetMapping("/activities")
    public ModelAndView getActivities(@TenantId long tenantId){
        LOGGER.info("YouthCouncilControllerMVC is running getActivities with tenantId {}", tenantId);
        return new ModelAndView("activities");
    }
}
