package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.WebPageViewModel;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.service.webpage.SectionService;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
@AllArgsConstructor
public class YCAdminDashboardController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final WebPageService webPageService;
    private final SectionService sectionService;
    private final ModelMapper modelMapper;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ModelAndView getAdminDashboard(@TenantId long tenantId){
        LOGGER.info("YCAdminDashboardController is running getAdminDashboard with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        List<WebPage> webPages = webPageService.getAllWebPagesByYouthCouncilId(tenantId);
        List<WebPageViewModel> webPageViewModels = webPages.stream().map(webPage -> modelMapper.map(webPage, WebPageViewModel.class)).toList();
        LOGGER.info("webPageViewModels: {}", webPageViewModels);
        modelAndView.addObject("webPages", webPageViewModels);
        return modelAndView;
    }

}
