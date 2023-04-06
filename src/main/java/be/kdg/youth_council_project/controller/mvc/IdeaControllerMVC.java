package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.service.IdeaService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/ideas")
public class IdeaControllerMVC {
    private final IdeaService ideaService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ModelAndView getAllIdeas(@TenantId long tenantId){
        List<Idea> ideas = ideaService.getIdeasByYouthCouncilId(tenantId);
        List<IdeaViewModel> ideaViewModels = ideas.stream().map(ideaService::mapToIdeaViewModel
        ).toList();
        return new ModelAndView("ideas", "ideas", ideaViewModels);
    }
}
