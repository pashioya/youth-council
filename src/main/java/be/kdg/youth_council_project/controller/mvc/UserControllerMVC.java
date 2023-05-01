package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.tenants.TenantId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    public UserControllerMVC(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getUser(@TenantId long tenantId){
        LOGGER.info("UserControllerMVC is running getUser with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("user-profile");
        modelAndView.addObject("one_user", userService.getUser(tenantId));
        return modelAndView;
    }
}
