package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.YouthCouncilViewModel;
import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.YouthCouncilService;
import be.kdg.youth_council_project.service.youth_council_items.MunicipalityService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@NoTenantController
@AllArgsConstructor
public class GeneralAdminDashboardControllerMVC {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final YouthCouncilService youthCouncilService;
    private final MunicipalityService municipalityService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showGeneralAdminDashboard() {
        LOGGER.info("GeneralAdminDashboardController is running showGeneralAdminDashboard");
        return new ModelAndView("ga/ga-dashboard");
    }

    @GetMapping
    public ModelAndView showLandingPage() {
        LOGGER.info("GeneralAdminDashboardController is running showLandingPage");
        return new ModelAndView("index");
    }

    @GetMapping("/dashboard/platforms")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showPlatforms() {
        LOGGER.info("GeneralAdminDashboardController is running showPlatforms");
        List<YouthCouncil> youthCouncils = youthCouncilService.getYouthCouncils();
        ModelAndView modelAndView = new ModelAndView("ga/ga-dashboard-platform");
        List<YouthCouncilViewModel> youthCouncilViewModels = youthCouncils.stream().map(
                        youthCouncil -> modelMapper.map(youthCouncil, YouthCouncilViewModel.class))
                .toList();
        List<Municipality> municipalities = municipalityService.getMunicipalities();
        modelAndView.addObject("municipalities", municipalities);
        modelAndView.addObject("youthCouncils", youthCouncilViewModels);
        return modelAndView;
    }

    @GetMapping("/dashboard/platforms/{id}")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showPlatform(@PathVariable int id) {
        LOGGER.info("GeneralAdminDashboardController is running showPlatform");
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        List<User> admins = userService.getAdminsByYouthCouncilId(id);
        YouthCouncilViewModel youthCouncilViewModel = modelMapper.map(youthCouncil, YouthCouncilViewModel.class);
        List<UserViewModel> adminViewModels = admins.stream().map(
                        admin -> modelMapper.map(admin, UserViewModel.class))
                .toList();
        ModelAndView modelAndView = new ModelAndView("ga/ga-dashboard-platform-details");
        modelAndView.addObject("youthCouncil", youthCouncilViewModel);
        modelAndView.addObject("admins", adminViewModels);
        return modelAndView;
    }

    @GetMapping("/dashboard/page-templates")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showPageTemplates() {
        LOGGER.info("GeneralAdminDashboardController is running showPageTemplates");
        return new ModelAndView("ga/ga-dashboard-page-templates");
    }

    @GetMapping("dashboard/page-templates/create")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showCreateTemplate() {
        LOGGER.info("GeneralAdminDashboardController is running showCreateTemplate");
        return new ModelAndView("ga/ga-dashboard-create-page-template");
    }

}
