package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.tenants.NoTenantController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@NoTenantController
@Controller
public class IndexController {
    @GetMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("index");
    }
}
