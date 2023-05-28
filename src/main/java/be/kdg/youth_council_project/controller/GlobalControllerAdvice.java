package be.kdg.youth_council_project.controller;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.service.YouthCouncilService;
import be.kdg.youth_council_project.tenants.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.EntityNotFoundException;
import java.util.Base64;

@ControllerAdvice
public class GlobalControllerAdvice {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final YouthCouncilService youthCouncilService;

    public GlobalControllerAdvice(YouthCouncilService youthCouncilService) {
        this.youthCouncilService = youthCouncilService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception e) {
        LOGGER.info("GlobalControllerAdvice is running handleEntityNotFoundException");
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ModelAttribute("tenant")
    public String populateTenantName(@Tenant String tenant) {
        if (tenant != null) {
            YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilBySlug(tenant);
            return youthCouncil.getName();
        }
        return null;
    }

    @ModelAttribute("logo")
    public String populateLogo(@Tenant String tenant) {
        if (tenant != null) {
            YouthCouncil youthCouncil = youthCouncilService.getYouthCouncilBySlug(tenant);
            return Base64.getEncoder().encodeToString(youthCouncil.getLogo());
        }
        return null;
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public String handleUserNotFoundException(UsernameNotFoundException e) {
        LOGGER.debug("GlobalControllerAdvice is running handleUserNotFoundException because of {}", e.getMessage());
        // TODO: redirect to error page or just a meaningful status code...
        return "redirect:/";
    }

    @ExceptionHandler(value = Exception.class)
    public String handleException(Exception e) {
        LOGGER.debug("GlobalControllerAdvice is running handleException because of {}", e.getMessage());
        return "redirect:/";
    }

}
