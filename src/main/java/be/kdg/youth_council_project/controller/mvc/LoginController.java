package be.kdg.youth_council_project.controller.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/login")
    public ModelAndView showLogin(){
        LOGGER.info("LoginController is running showLogin");
        var mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView signUp() {
        LOGGER.info("LoginController is running signUp");
        return new ModelAndView("sign-up");
    }
}
