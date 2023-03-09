package be.kdg.youth_council_project.controller;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.service.YouthCouncilService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@RequestMapping("/youth-councils")
public class YouthCouncilController {
    private final YouthCouncilService youthCouncilService;
    @GetMapping("/youth-councils/{id}")
    public ModelAndView getYouthCouncil(@PathVariable long id){
        YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilById(id);
        return new ModelAndView("youth-council", "youthCouncil", youthCouncil);
    }

}
