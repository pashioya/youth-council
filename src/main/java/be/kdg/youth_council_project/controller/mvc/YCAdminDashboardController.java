package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.*;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPointStatus;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import be.kdg.youth_council_project.service.youth_council_items.ActionPointService;
import be.kdg.youth_council_project.service.youth_council_items.ElectionService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.StandardActionService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@AllArgsConstructor
public class YCAdminDashboardController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final WebPageService webPageService;
    private final ActionPointService actionPointService;
    private final IdeaService ideaService;
    private final StandardActionService standardActionService;
    private final UserService userService;
    private final ElectionService electionService;
    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getAdminDashboard(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getAdminDashboard with tenantId {}", tenantId);
        return new ModelAndView("yc-admin/yc-dashboard");
    }

    @GetMapping("/webpages/{webpageId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getWebPage(@TenantId long tenantId, @PathVariable long webpageId) {
        LOGGER.info("YCAdminDashboardController is running getWebPage with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-webpage");
        WebPage webPage = webPageService.getWebPageById(webpageId);
        WebPageViewModel webPageViewModel = modelMapper.map(webPage, WebPageViewModel.class);
        modelAndView.addObject("webPage", webPageViewModel);
        return modelAndView;
    }

    @GetMapping("/pages")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getPages(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getPages with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-pages");
        List<WebPage> webPages = webPageService.getAllWebPagesByYouthCouncilId(tenantId);
        List<WebPageViewModel> webPageViewModels = webPages.stream().map(webPage -> modelMapper.map(webPage, WebPageViewModel.class)).toList();
        modelAndView.addObject("webPages", webPageViewModels);
        return modelAndView;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getUsers(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getUsers with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-users");
        List<User> allUsers = userService.getAllNonDeletedUsersForYouthCouncil(tenantId);
        modelAndView.addObject("users",
                allUsers.stream()
                        .map(user -> modelMapper.map(user, UserViewModel.class))
                        .toList());
        return modelAndView;
    }

    @GetMapping("/manage-content")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getManageContentPlatform(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getManageContentPlatform with tenantId {}", tenantId);
        return new ModelAndView("yc-admin/yc-manage-content");
    }

    @GetMapping("/manage-content/action-points/{actionPointId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getManageContentActionPoints(@TenantId long tenantId, @PathVariable long actionPointId) {
        LOGGER.info("YCAdminDashboardController is running getManageContentActionPoints with tenantId {}", tenantId);
        ActionPoint actionPoint = actionPointService.getActionPointById(actionPointId, tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/manage-entity/yc-manage-action-point");
        ActionPointViewModel actionPointViewModel = actionPointService.mapToViewModel(actionPoint, null);
        modelAndView.addObject("actionPoint", actionPointViewModel);

        modelAndView.addObject("statuses", ActionPointStatus.values());

        modelAndView.addObject("standardActions", standardActionService.getAllStandardActions()
                .stream()
                .map(standardActionService::mapToViewModel).toList());

        modelAndView.addObject("ideas", ideaService.getIdeasByYouthCouncilId(tenantId)
                .stream()
                .map(idea -> new LinkedIdeaViewModel(idea.getId(), idea.getDescription())).toList());

        return modelAndView;
    }

    @GetMapping("/social-media")
    public ModelAndView getSocialMedia(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getSocialMedia with tenantId {}", tenantId);
        return new ModelAndView("yc-admin/yc-social-media");
    }

    @GetMapping("/manage-content/elections/{electionId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getManageContentElections(@TenantId long tenantId, @PathVariable long electionId) {
        LOGGER.info("YCAdminDashboardController is running getManageContentElections with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/manage-entity/yc-manage-election");
        Election election = electionService.getElectionById(electionId, tenantId);
        ElectionViewModel electionViewModel = modelMapper.map(election, ElectionViewModel.class);
        modelAndView.addObject("election", electionViewModel);
        return modelAndView;
    }
}
