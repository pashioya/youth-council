package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ThemeViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.ThemeService;
import be.kdg.youth_council_project.service.YouthCouncilService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/ideas")
public class IdeaControllerMVC {
    private final YouthCouncilService youthCouncilService;
    private final IdeaService ideaService;
    private final ThemeService themeService;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public ModelAndView getAllIdeas(@TenantId long tenantId, @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeaControllerMVC is running getAllIdeas");
        List<Idea> ideas = ideaService.getIdeasByYouthCouncilId(tenantId);
        List<IdeaViewModel> ideaViewModels = ideas.stream().map(idea -> {
                    idea.setImages(ideaService.getImagesOfIdea(idea.getId()));
                    IdeaViewModel ideaViewModel = modelMapper.map(idea, IdeaViewModel.class);
                    if (user != null) {
                        ideaViewModel.setLikedByUser(ideaService.isLikedByUser(idea.getId(), user.getUserId()));
                    }
                    return ideaViewModel;
                }
        ).toList();

        List<Theme> themes = themeService.getAllThemes();
        List<ThemeViewModel> themeViewModels = themes.stream().map(theme -> modelMapper.map(theme, ThemeViewModel.class)).toList();
        Map<String, Object> model = new HashMap<>();
        model.put("ideas", ideaViewModels);
        model.put("themes", themeViewModels);
        return new ModelAndView("modules/ideas", model);
    }

}
