package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.MunicipalityViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.MunicipalityService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final MunicipalityService municipalityService;

    @GetMapping("/user-ideas")
    public ModelAndView getUserIdeas(@TenantId long tenantId) {
        LOGGER.info("YouthCouncilControllerMVC is running getUserIdeas with tenantId {}", tenantId);
        return new ModelAndView("/user/user-ideas");
    }

    @GetMapping("/profile")
    public ModelAndView getSettings(@TenantId long tenantId,
                                    @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("YouthCouncilControllerMVC is running getSettings with tenantId {}", tenantId);
        User user1 = userService.getUserById(user.getUserId());
        ModelAndView modelAndView = new ModelAndView("user/user-profile");
        UserViewModel userViewModel = modelMapper.map(user1, UserViewModel.class);
        Municipality municipality = municipalityService.getMunicipalitiesByYouthCouncilId(tenantId);
        MunicipalityViewModel municipalityViewModel = modelMapper.map(municipality, MunicipalityViewModel.class);
        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("municipality", municipalityViewModel);
        return modelAndView;
    }
}
