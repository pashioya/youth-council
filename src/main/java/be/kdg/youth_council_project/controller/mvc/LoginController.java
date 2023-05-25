package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.api.dtos.NewUserDto;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.MunicipalityService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;
    private final MunicipalityService municipalityService;

    @GetMapping("/login")
    public ModelAndView showLogin() {
        LOGGER.info("LoginController is running showLogin");
        var mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(
            @Valid NewUserDto newUserDto,
            HttpServletRequest request,
            @TenantId long tenantId
    ) {
        LOGGER.info("LoginController is running signUp");
        User user = new User(
                newUserDto.getEmail(),
                newUserDto.getUsername(),
                newUserDto.getPassword(),
                newUserDto.getFirstName(),
                newUserDto.getLastName(),
                newUserDto.getPostCode(),
                false
        );
        User createdUser = userService.saveUser(user, tenantId);
        try {
            request.login(createdUser.getUsername()
                    , newUserDto.getPassword());
        } catch (ServletException e) {
            LOGGER.error("Error while login ", e);
        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/register")
    @Transactional
    public ModelAndView signUp() {
        LOGGER.info("LoginController is running signUp");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign-up");
        List<Integer> postCodes = new ArrayList<>();
        municipalityService.getAllMunicipalities().forEach(municipality -> postCodes.addAll(municipality.getPostCodes()));
        postCodes.sort(Integer::compareTo);
        modelAndView.addObject("postCodes", postCodes);
        return modelAndView;
    }


}
