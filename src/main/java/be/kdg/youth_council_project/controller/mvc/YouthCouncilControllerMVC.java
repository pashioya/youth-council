package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.NewsItemViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.SectionViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.WebPageViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.service.youth_council_items.NewsItemService;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class YouthCouncilControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageService webPageService;
    private final NewsItemService newsItemService;
    private final ModelMapper modelMapper;
    @GetMapping
    public ModelAndView getYouthCouncil(@TenantId long tenantId){
        LOGGER.info("YouthCouncilControllerMVC is running getYouthCouncil with tenantId {}", tenantId);
        WebPage webPage = webPageService.getHomePageByYouthCouncilId(tenantId);
        ModelAndView modelAndView = new ModelAndView("youth-council");
        List<NewsItem> newsItems = newsItemService.getNewsItemsByYouthCouncilId(tenantId);

        modelMapper.map(newsItems, NewsItemViewModel.class);
        modelMapper.map(webPage, WebPageViewModel.class);
        modelMapper.map(webPage.getSections(), SectionViewModel.class);

        modelAndView.addObject("sections", webPage.getSections());
        modelAndView.addObject("newsItems", newsItems);
        modelAndView.addObject("webPage", webPage);
        return modelAndView;
    }


    @GetMapping("/elections")
    public ModelAndView getElections(@TenantId long tenantId){
        LOGGER.info("YouthCouncilControllerMVC is running getElections with tenantId {}", tenantId);
        return new ModelAndView("elections");
    }
}
