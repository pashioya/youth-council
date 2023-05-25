package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ThemeStandardActionViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.UserViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.YouthCouncilViewModel;
import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.YouthCouncilService;
import be.kdg.youth_council_project.service.youth_council_items.MunicipalityService;
import be.kdg.youth_council_project.service.youth_council_items.StandardActionService;
import be.kdg.youth_council_project.service.youth_council_items.ThemeService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@NoTenantController
@AllArgsConstructor
@RequestMapping("/dashboard")
public class GeneralAdminDashboardControllerMVC {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final YouthCouncilService youthCouncilService;
    private final MunicipalityService municipalityService;
    private final ThemeService themeService;
    private final StandardActionService standardActionService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showGeneralAdminDashboard() {
        LOGGER.info("GeneralAdminDashboardController is running showGeneralAdminDashboard");
        return new ModelAndView("ga/ga-dashboard");
    }


    @GetMapping("/platforms")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showPlatforms() {
        LOGGER.info("GeneralAdminDashboardController is running showPlatforms");
        List<YouthCouncil> youthCouncils = youthCouncilService.getYouthCouncils();
        ModelAndView modelAndView = new ModelAndView("ga/ga-dashboard-platform");
        List<YouthCouncilViewModel> youthCouncilViewModels = youthCouncils.stream().map(
                        youthCouncil -> modelMapper.map(youthCouncil, YouthCouncilViewModel.class))
                .toList();
        List<Municipality> municipalities = municipalityService.getAllMunicipalities();
        municipalities.removeIf(municipality -> youthCouncils.stream().anyMatch(
                youthCouncil -> Objects.equals(youthCouncil.getMunicipality().getId(), municipality.getId())));
        modelAndView.addObject("municipalities", municipalities);
        modelAndView.addObject("youthCouncils", youthCouncilViewModels);
        return modelAndView;
    }

    @GetMapping("/platforms/{id}")
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

    @GetMapping("/page-templates")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showPageTemplates() {
        LOGGER.info("GeneralAdminDashboardController is running showPageTemplates");
        return new ModelAndView("ga/ga-dashboard-page-templates");
    }

    @GetMapping("/page-templates/create")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showCreateTemplate() {
        LOGGER.info("GeneralAdminDashboardController is running showCreateTemplate");
        return new ModelAndView("ga/ga-dashboard-create-page-template");
    }

    @GetMapping("/themes")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showThemes() {
        LOGGER.info("GeneralAdminDashboardController is running showThemes");

        List<Theme> themes = themeService.getAllThemes();
        List<ThemeStandardActionViewModel> themesViewModels = themes.stream()
                .map(theme -> {
                    ThemeStandardActionViewModel themeStandardActionViewModel = new ThemeStandardActionViewModel();
                    themeStandardActionViewModel.setId(theme.getId());
                    themeStandardActionViewModel.setTheme(theme);
                    themeStandardActionViewModel.setStandardActions(standardActionService.getStandardActionsByThemeId(theme.getId()));
                    return themeStandardActionViewModel;
                })
                .toList();

        return new ModelAndView("ga/ga-dashboard-themes")
                .addObject("themes", themesViewModels);
    }

    @GetMapping("/themes/{id}")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ModelAndView showTheme(@PathVariable long id) {
        LOGGER.info("GeneralAdminDashboardController is running showTheme");
        Theme theme = themeService.getThemeById(id);
        List<StandardAction> standardActions = standardActionService.getStandardActionsByThemeId(id);
        ModelAndView modelAndView = new ModelAndView("ga/ga-dashboard-theme-details");
        modelAndView.addObject("theme", theme);
        modelAndView.addObject("standardActions", standardActions);
        return modelAndView;
    }
}
