package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.tenants.NoTenantController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@NoTenantController
public class GeneralAdminDashboardController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/dashboard")
    public ModelAndView showDashboard() {
        LOGGER.info("GeneralAdminDashboardController is running showDashboard");
        return new ModelAndView("general-admin-dashboard");
    }
}
