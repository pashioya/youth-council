package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.WebPageViewModel;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.tenants.TenantId;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class UserControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private UserService userService;
    private final ModelMapper modelMapper;

    public UserControllerMVC(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{username}")
    public ModelAndView getUser(@TenantId long tenantId, @PathVariable String username) {
        LOGGER.info("UserControllerMVC is running getUser with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("user-profile");
        User user = userService.getUserByNameAndYouthCouncilId(username, tenantId);
        UserViewModel userViewModel = modelMapper.map(user, UserViewModel.class);
        modelAndView.addObject("user", userViewModel);
        return modelAndView;
    }
}
