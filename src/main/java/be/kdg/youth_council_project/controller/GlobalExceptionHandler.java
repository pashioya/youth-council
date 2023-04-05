package be.kdg.youth_council_project.controller;

import be.kdg.youth_council_project.tenants.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.logging.Level;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception e){
        LOGGER.info("GlobalExceptionHandler is running handleEntityNotFoundException");
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ModelAttribute("tenant")
    public String populateTenantName(@Tenant String tenant) {
        return tenant;
    }

//    @ExceptionHandler(value = TenantNotFoundException.class)
//    public String handleTenantNotFoundException(TenantNotFoundException e) {
//        LOGGER.debug("GlobalExceptionHandler is running handleTenantNotFoundException because of {}", e.getMessage());
//        // TODO: redirect to error page or just a meaningful status code...
//        return "redirect:/";
//    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public String handleUserNotFoundException(UsernameNotFoundException e) {
        LOGGER.debug("GlobalExceptionHandler is running handleUserNotFoundException because of {}", e.getMessage());
        // TODO: redirect to error page or just a meaningful status code...
        return "redirect:/";
    }


}