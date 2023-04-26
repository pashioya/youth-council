package be.kdg.youth_council_project.controller.mvc;

import be.kdg.youth_council_project.controller.api.dtos.NewUserDto;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.tenants.TenantId;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@AllArgsConstructor
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

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

        System.out.println(createdUser);

        try {
            request.login(createdUser.getUsername()
                    , newUserDto.getPassword());
        } catch (ServletException e) {
            LOGGER.error("Error while login ", e);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/register")
    public ModelAndView signUp() {
        LOGGER.info("LoginController is running signUp");
        File file = new File("src/main/resources/static/json/BelgiumPostCodes.json");
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            ByteArrayResource postCodesResource = new ByteArrayResource(data);
            ObjectMapper mapper = new ObjectMapper();
            List postCodesList = mapper.readValue(postCodesResource.getInputStream(), List.class);
            return new ModelAndView("sign-up", "postCodes", postCodesList);
        } catch (IOException e) {
            LOGGER.error("Failed to read file: {}", e.getMessage());
        }
        return new ModelAndView("sign-up");
    }


}
