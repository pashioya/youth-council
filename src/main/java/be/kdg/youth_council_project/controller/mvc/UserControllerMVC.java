package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.tenants.TenantId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;
    private final ModelMapper modelMapper;

    public UserControllerMVC(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

//    @GetMapping("/users")
//    public ModelAndView getUsers(long id) {
//        LOGGER.info("UserControllerMVC is running getUsers");
//
//        User users = userService.getUserById(id);
//        UserViewModel userViewModel = modelMapper.map(users, UserViewModel.class);
//        modelAndView.addObject("users", userViewModel);
//        return modelAndView;
//    }

    @GetMapping("/{username}")
    public ModelAndView getUser(@PathVariable("username") String username, @TenantId long tenantId) {
        LOGGER.info("UserControllerMVC is running getUserById");
        ModelAndView modelAndView = new ModelAndView("user-profile");
        ModelAndView modelAndView1 = new ModelAndView("fragments/header");
        User user = userService.getUserByNameAndYouthCouncilId(username, tenantId);
        UserViewModel userViewModel = modelMapper.map(user, UserViewModel.class);
        modelAndView.addObject("user", userViewModel);
        modelAndView1.addObject("users", userViewModel);
        return modelAndView;
    }
}
