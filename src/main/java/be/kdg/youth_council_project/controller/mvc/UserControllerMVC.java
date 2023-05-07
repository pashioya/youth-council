package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.service.UserService;
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

    @GetMapping("/{userId}")
    public ModelAndView getUser(@PathVariable long userId){
        LOGGER.info("UserControllerMVC is running getUser with tenantId {}", userId);
        var mav = new ModelAndView();
        mav.setViewName("user-profile");
        mav.addObject("one_user", userService.getUser(userId));
        return mav;

    }
}
