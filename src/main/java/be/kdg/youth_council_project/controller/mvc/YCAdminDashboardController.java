package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.WebPageViewModel;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.service.webpage.WebPageService;
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
    private final ModelMapper modelMapper;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getAdminDashboard(@TenantId long tenantId){
        LOGGER.info("YCAdminDashboardController is running getAdminDashboard with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-dashboard");
        return modelAndView;
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

    @GetMapping("/modules")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getModules(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getModules with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-modules");
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
        return modelAndView;
    }

    @GetMapping("/manage-content")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getManageContentPlatform(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getManageContentPlatform with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-manage-content");
        return modelAndView;
    }

    @GetMapping("/visitors")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getVisitors(@TenantId long tenantId) {
        LOGGER.info("YCAdminDashboardController is running getVisitors with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("yc-admin/yc-visitors");
        return modelAndView;
    }

}
