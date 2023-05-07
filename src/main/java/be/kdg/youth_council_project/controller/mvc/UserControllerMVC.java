package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.tenants.TenantId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;

    public UserControllerMVC(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ModelAndView getUser(@PathVariable String username, @TenantId long tenantId) {
        LOGGER.info("UserControllerMVC is running getUser");
        var user = userService.getUserByNameAndYouthCouncilId(username, tenantId);
        var mav = new ModelAndView();
        mav.setViewName("user-profile");
        mav.addObject("one_user",
                new UserViewModel(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPostCode(),
                        user.getPassword()));
        return mav;
    }
}
