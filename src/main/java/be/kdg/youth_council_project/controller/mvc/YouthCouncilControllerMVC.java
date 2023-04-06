package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
@AllArgsConstructor
@RequestMapping
public class YouthCouncilControllerMVC {
    private final Logger logger = Logger.getLogger(YouthCouncilControllerMVC.class.getName());
    @GetMapping
    public ModelAndView getYouthCouncil(@TenantId long tenantId){
        logger.info("Youth council with id " + tenantId + " was requested");
        return new ModelAndView("youth-council");
    }
    @GetMapping("/elections")
    public ModelAndView getElections(@TenantId long tenantId){
        logger.info("Elections for youth council with id " + tenantId + " were requested");
        return new ModelAndView("elections");
    }
    @GetMapping("/admin-dashboard")
    public ModelAndView getAdminDashboard(@TenantId long tenantId){
        logger.info("Admin dashboard for youth council with id " + tenantId + " was requested");
        return new ModelAndView("admin-dashboard");
    }
    @GetMapping("/activities")
    public ModelAndView getActivities(@TenantId long tenantId){
        logger.info("Activities for youth council with id " + tenantId + " were requested");
        return new ModelAndView("activities");
    }
}
