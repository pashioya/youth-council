package be.kdg.youth_council_project.security;

import be.kdg.youth_council_project.domain.platform.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws  IOException {

        LOGGER.info("LoginSuccessHandler is running onAuthenticationSuccess");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String redirectURL = request.getContextPath();
//        dont login if user has role deleted
        if (userDetails.hasRole(Role.DELETED.getCode())) {
            LOGGER.debug("Deleted user trying to log in");
            request.getSession().invalidate();
            redirectURL = "login";
        } else
        if (userDetails.hasRole(Role.GENERAL_ADMINISTRATOR.getCode())) {
            LOGGER.debug("General admin logging in");
            redirectURL = "dashboard";
        } else if (userDetails.hasRole(Role.YOUTH_COUNCIL_ADMINISTRATOR.getCode())) {
            LOGGER.debug("Youth council admin logging in");
            redirectURL = "dashboard";
        } else {
            LOGGER.debug("Youth council moderator or user logging in");
            redirectURL = "";
        }
        LOGGER.debug("Redirecting to {}", redirectURL);
        response.sendRedirect(redirectURL);
    }

}