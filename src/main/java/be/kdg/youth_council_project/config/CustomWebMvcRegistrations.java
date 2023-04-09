package be.kdg.youth_council_project.config;

import be.kdg.youth_council_project.tenants.TenantRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Component
public class CustomWebMvcRegistrations implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new TenantRequestMappingHandlerMapping();
    }


}
