package be.kdg.youth_council_project.tenants;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class TenantRequestMappingHandlerMapping extends RequestMappingHandlerMapping {


    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        // if the given class is annotated with SubdomainController, we prefer it
        NoTenantController noTenantController = null;

        if ((noTenantController = AnnotationUtils.findAnnotation(handlerType, NoTenantController.class)) != null) {
            // we need to extract the mapped subdomains as we don't have access to this
            // information during runtime
            return new TenantRequestCondition();
        }


        // no SubdomainController or NoTenantController annotation? Then don't use any
        // conditions and fallback to the default handling without any condiitions.
        return null;
    }
}
