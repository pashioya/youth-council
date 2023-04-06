package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.NewsItemViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.service.NewsItemService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
@RequestMapping("/news-items")
public class NewsItemControllerMVC {
    private final NewsItemService newsItemService;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = Logger.getLogger(NewsItemControllerMVC.class.getName());
    @GetMapping
    public ModelAndView getAllNewsItems(@TenantId long tenantId){
        LOGGER.info("Getting all news items for youth council with id: " + tenantId);
        List<NewsItem> newsItems = newsItemService.getNewsItemsByYouthCouncilId(tenantId);
        List<NewsItemViewModel> newsItemViewModels = newsItems.stream().map(newsItemService::mapToNewsItemViewModel
        ).toList();
        return new ModelAndView("news-items", "newsItems", newsItemViewModels);
    }


}
