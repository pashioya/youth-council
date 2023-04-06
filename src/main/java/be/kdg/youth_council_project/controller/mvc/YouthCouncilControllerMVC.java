package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping
public class YouthCouncilControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @GetMapping
    public ModelAndView getYouthCouncil(@TenantId long tenantId){
        LOGGER.info("Youth council with id " + tenantId + " was requested");
        return new ModelAndView("youth-council");
    }
    @GetMapping("/elections")
    public ModelAndView getElections(@TenantId long tenantId){
        LOGGER.info("Elections for youth council with id " + tenantId + " were requested");
        return new ModelAndView("elections");
    }
    @GetMapping("/admin-dashboard")
    public ModelAndView getAdminDashboard(@TenantId long tenantId){
        LOGGER.info("Admin dashboard for youth council with id " + tenantId + " was requested");
        return new ModelAndView("admin-dashboard");
    }
    @GetMapping("/activities")
    public ModelAndView getActivities(@TenantId long tenantId){
        LOGGER.info("Activities for youth council with id " + tenantId + " were requested");
        return new ModelAndView("activities");
    }
}
