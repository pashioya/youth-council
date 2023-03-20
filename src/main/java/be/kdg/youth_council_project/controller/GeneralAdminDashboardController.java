package be.kdg.youth_council_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GeneralAdminDashboardController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {
        LOGGER.info("You have reached the admin dashboard");
        return new ModelAndView("general-admin-dashboard");
    }
}
