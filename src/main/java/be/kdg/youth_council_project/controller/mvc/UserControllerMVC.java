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

//    @GetMapping
//    public ModelAndView getAllUsers() {
//        LOGGER.info("Getting all users");
//        var modelAndView = new ModelAndView();
//        modelAndView.setViewName("fragments/header");
//        modelAndView.addObject("users",
//                userService.getAllUsers()
//                        .stream()
//                        .map(user -> new UserViewModel(
//                                user.getId(),
//                                user.getFirstName(),
//                                user.getLastName(),
//                                user.getUsername(),
//                                user.getEmail(),
//                                user.getPostCode(),
//                                user.getPassword()))
//                        .toList());
//        return modelAndView;
//    }
}
