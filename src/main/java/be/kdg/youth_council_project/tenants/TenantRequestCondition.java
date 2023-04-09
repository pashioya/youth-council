package be.kdg.youth_council_project.tenants;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class TenantRequestCondition implements RequestCondition<TenantRequestCondition> {

    @Override
    public TenantRequestCondition combine(TenantRequestCondition other) {
        return null;
    }

    @Override
    public TenantRequestCondition getMatchingCondition(HttpServletRequest request) {
        String tenant = TenantContext.getCurrentTenant();
        if (tenant != null ){
            return null;
        }
        return this;
    }

    @Override
    public int compareTo(TenantRequestCondition other, HttpServletRequest request) {
        return 0;
    }
}
