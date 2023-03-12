package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.StyleViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.WebPageViewModel;
import be.kdg.youth_council_project.controller.mvc.viewmodels.YouthCouncilViewModel;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.service.YouthCouncilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
@RequestMapping("/youth-councils")
public class YouthCouncilController {
    private final YouthCouncilService youthCouncilService;
    private final Logger logger = Logger.getLogger(YouthCouncilController.class.getName());
    @GetMapping("{id}")
    public ModelAndView getYouthCouncil(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Youth council with id " + id + " was requested");
        return new ModelAndView("youth-council", "youthCouncil", new YouthCouncilViewModel(
                youthCouncil.getId(),
                youthCouncil.getName(),
                youthCouncil.getLogo(),
                youthCouncil.getMunicipalityName(),
                new WebPageViewModel(
                        youthCouncil.getHomePage()
                )
        ));
    }
    @GetMapping("{id}/ideas")
    public ModelAndView getIdeas(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        // TODO: Fix this, so ideas can be represented on the web
//        List<IdeaViewModel> ideas = youthCouncil.getIdeas().stream().map(
//                idea -> new IdeaViewModel(
//                        idea.getId(),
//                        idea.getDescription(),
//                        idea.getAuthor().getFirstName() + " " + idea.getAuthor().getLastName(),
//                        idea.getAuthor().getId(),
//                        idea.getActionPoint().getTitle(),
//                        idea.getTheme().getName(),
//                        idea.getImages(),
//                        idea.getDateAdded(),
//                        idea.getLikes()
//                )).toList();
        logger.info("Ideas for youth council with id " + id + " were requested");
        return new ModelAndView("ideas", "youthCouncil", new YouthCouncilViewModel(
                youthCouncil.getId(),
                youthCouncil.getName(),
                youthCouncil.getLogo(),
                youthCouncil.getMunicipalityName(),
                new WebPageViewModel(
                        youthCouncil.getHomePage()
                )
        ));
    }
    @GetMapping("{id}/action-points")
    public ModelAndView getActionPoints(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Action points for youth council with id " + id + " were requested");
        return new ModelAndView("action-points", "youthCouncil", youthCouncil);
    }
    @GetMapping("{id}/questionnaire")
    public ModelAndView getQuestionnaire(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Questionnaire for youth council with id " + id + " was requested");
        return new ModelAndView("questionnaire", "youthCouncil", youthCouncil);
    }
    @GetMapping("{id}/elections")
    public ModelAndView getElections(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Elections for youth council with id " + id + " were requested");
        return new ModelAndView("elections", "youthCouncil", youthCouncil);
    }
}
