package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ThemeViewModel;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.controller.mvc.viewmodels.ThemeViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import be.kdg.youth_council_project.service.IdeaService;
import be.kdg.youth_council_project.service.ThemeService;
import be.kdg.youth_council_project.service.YouthCouncilService;
import be.kdg.youth_council_project.tenants.TenantId;
import be.kdg.youth_council_project.service.ThemeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ModelAndView getAllIdeas(@TenantId long tenantId){
        LOGGER.info("IdeaControllerMVC is running getAllIdeas");
        List<Idea> ideas = ideaService.getIdeasByYouthCouncilId(tenantId);
        ideas.forEach(idea -> {
            idea.setImages(ideaService.getImagesOfIdea(idea.getId()));
            System.out.println(idea.getDescription());
            System.out.println(idea.getImages());
        });
        String municipalityName = youthCouncilService.getYouthCouncilById(tenantId).getMunicipalityName();
        List<IdeaViewModel> ideaViewModels = ideas.stream().map(idea -> modelMapper.map(idea, IdeaViewModel.class)
        ).toList();
        List<Theme> themes = themeService.getAllThemes();
        List<ThemeViewModel> themeViewModels = themes.stream().map(theme -> modelMapper.map(theme, ThemeViewModel.class)).toList();
        Map<String, Object> model = new HashMap<>();
        model.put("municipalityName", municipalityName);
        model.put("ideas", ideaViewModels);
        model.put("themes", themeViewModels);
        return new ModelAndView("ideas", model);
    }

}
