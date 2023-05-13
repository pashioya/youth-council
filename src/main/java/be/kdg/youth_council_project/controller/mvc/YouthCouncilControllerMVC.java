package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.*;
import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import be.kdg.youth_council_project.domain.webpage.WebPage;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.MunicipalityService;
import be.kdg.youth_council_project.service.youth_council_items.NewsItemService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
public class YouthCouncilControllerMVC {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageService webPageService;
    private final NewsItemService newsItemService;
    private final UserService userService;
    private final MunicipalityService municipalityService;
    private  final IdeaService ideaService;
    private final ModelMapper modelMapper;


    @GetMapping
    public ModelAndView getYouthCouncil(@TenantId long tenantId) {
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
    public ModelAndView getElections(@TenantId long tenantId) {
        LOGGER.info("YouthCouncilControllerMVC is running getElections with tenantId {}", tenantId);
        return new ModelAndView("modules/elections");
    }

    @GetMapping("/info-pages")
    public ModelAndView getInfoPages(@TenantId long tenantId) {
        LOGGER.info("YouthCouncilControllerMVC is running getInfoPages with tenantId {}", tenantId);
        List<WebPage> informativePages = webPageService.getInformativePagesByYouthCouncilId(tenantId);
        ModelAndView modelAndView = new ModelAndView("info-pages");
        List<WebPageViewModel> informativePageViewModels =
                informativePages.stream().map(webPage -> modelMapper.map(webPage,
                        WebPageViewModel.class)).toList();
        modelAndView.addObject("webPages", informativePageViewModels);
        return modelAndView;
    }

    @GetMapping("/info-pages/{webPageId}")
    public ModelAndView getInfoPage(@TenantId long tenantId, @PathVariable long webPageId) {
        LOGGER.info("YouthCouncilControllerMVC is running getInfoPage with tenantId {} and webPageId {}", tenantId, webPageId);
        WebPage webPage = webPageService.getWebPageById(webPageId);
        ModelAndView modelAndView = new ModelAndView("info-page");
        modelMapper.map(webPage, WebPageViewModel.class);
        modelMapper.map(webPage.getSections(), SectionViewModel.class);
        modelAndView.addObject("sections", webPage.getSections());
        modelAndView.addObject("webPage", webPage);
        return modelAndView;
    }

    @GetMapping("/settings")
    public ModelAndView getSettings(@TenantId long tenantId,
                                    @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("YouthCouncilControllerMVC is running getSettings with tenantId {}", tenantId);
        User user1 = userService.getUserById(user.getUserId());
        ModelAndView modelAndView = new ModelAndView("/user/user-settings");
        UserViewModel userViewModel = modelMapper.map(user1, UserViewModel.class);
        Municipality municipality = municipalityService.getMunicipalitiesByYouthCouncilId(tenantId);
        MunicipalityViewModel municipalityViewModel = modelMapper.map(municipality, MunicipalityViewModel.class);
        modelAndView.addObject("user", userViewModel);
        modelAndView.addObject("municipality", municipalityViewModel);
        return modelAndView;
    }

    @GetMapping("/user-ideas")
    public ModelAndView getUserIdeas(@TenantId long tenantId,
                                     @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("YouthCouncilControllerMVC is running getUserIdeas with tenantId {}", tenantId);
        ModelAndView modelAndView = new ModelAndView("/user/user-ideas");
        return modelAndView;
    }

//    @GetMapping("/author")
//    public ModelAndView getAuthor(@RequestParam long id) {
//        var author = userService.findUserByIdeaId(id);
//        var mav = new ModelAndView();
//        mav.setViewName("author");
//        mav.addObject("author", author);
//        return mav;
//    }

    @GetMapping("/author")
    public ModelAndView getAuthor(@AuthenticationPrincipal CustomUserDetails user) {
        var author = ideaService.findUserByAuthorId(user.getUserId());
        var mav = new ModelAndView();
        mav.setViewName("author");
        mav.addObject("author", author);
        return mav;
    }
}
