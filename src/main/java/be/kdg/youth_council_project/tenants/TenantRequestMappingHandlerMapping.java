package be.kdg.youth_council_project.tenants;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class TenantRequestMappingHandlerMapping extends RequestMappingHandlerMapping {


    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        NoTenantController noTenantController = null;

        if ((noTenantController = AnnotationUtils.findAnnotation(handlerType, NoTenantController.class)) != null) {

            return new TenantRequestCondition();
        }
        // If no NoTenantController annotation, then use default handling without any conditions
        return null;
    }
}
