package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.mvc.viewmodels.ThemeViewModel;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Theme;
import be.kdg.youth_council_project.service.IdeaService;
import be.kdg.youth_council_project.service.ThemeService;
import be.kdg.youth_council_project.service.YouthCouncilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.logging.Logger;

@Controller
@AllArgsConstructor
@RequestMapping("/youth-councils/{id}")
public class YouthCouncilControllerMVC {
    private final YouthCouncilService youthCouncilService;
    private final IdeaService ideaService;
    private final ThemeService themeService;
    private final Logger logger = Logger.getLogger(YouthCouncilControllerMVC.class.getName());
    @GetMapping("")
    public ModelAndView getYouthCouncil(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Youth council with id " + id + " was requested");
        return new ModelAndView("youth-council");
    }
    @GetMapping("/ideas/submit")
    public ModelAndView getSubmitIdea(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        List<Theme> themes = themeService.getAllThemes();
        List<ThemeViewModel> themeViewModels = themes.stream().map(theme -> new ThemeViewModel(theme.getId(), theme.getName())).toList();
        logger.info("Submit idea for youth council with id " + id + " was requested");
        return new ModelAndView("forms/submit-idea", "themes", themeViewModels);
    }
    @GetMapping("/action-points")
    public ModelAndView getActionPoints(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Action points for youth council with id " + id + " were requested");
        return new ModelAndView("action-points");
    }

    @GetMapping("/action-points/submit")
    public ModelAndView getActionPoint(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Submit action point for youth council with id " + id + " was requested");
        return new ModelAndView("forms/submit-action-point");
    }

    @GetMapping("/elections")
    public ModelAndView getElections(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Elections for youth council with id " + id + " were requested");
        return new ModelAndView("elections");
    }
    @GetMapping("/admin-dashboard")
    public ModelAndView getAdminDashboard(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Admin dashboard for youth council with id " + id + " was requested");
        return new ModelAndView("admin-dashboard");
    }
    @GetMapping("/news")
    public ModelAndView getNews(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("News for youth council with id " + id + " were requested");
        return new ModelAndView("news");
    }
    @GetMapping("/activities")
    public ModelAndView getActivities(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        logger.info("Activities for youth council with id " + id + " were requested");
        return new ModelAndView("activities");
    }
}
