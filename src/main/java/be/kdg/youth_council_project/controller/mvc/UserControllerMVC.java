package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerMVC {
    private UserService userService;

    public UserControllerMVC(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ModelAndView oneUser(@PathVariable long userId) {
        var user = userService.getUser(userId);
        var mav = new ModelAndView();
        mav.setViewName("user-profile");
        mav.addObject("one_user",
                new UserViewModel(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPostCode(),
                        user.getPassword()));
        return mav;
    }

}
