package be.kdg.youth_council_project.tenants;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TenantFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final YouthCouncilRepository youthCouncilRepository;

    public TenantFilter(YouthCouncilRepository youthCouncilRepository) {
        this.youthCouncilRepository = youthCouncilRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        LOGGER.info("TenantFilter is running doFilterInternal");
        var tenant = getTenant(request);
        var tenantId = youthCouncilRepository.findBySlug(tenant).map(YouthCouncil::getId).orElse(null);
        if (tenant != null && tenantId == null) {
            // Attempted access to non-existing tenant
            chain.doFilter(request, response);
            return;
        }
        LOGGER.debug("Setting tenant: " + tenant + " (domain " + request.getServerName() + ")");
        LOGGER.debug("Setting tenant ID: " + tenantId);
        TenantContext.setCurrentTenant(tenant);
        TenantContext.setCurrentTenantId(tenantId);
        chain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/webjars/")
                || request.getRequestURI().startsWith("/css/")
                || request.getRequestURI().startsWith("/js/")
                || request.getRequestURI().endsWith(".ico");
    }

    public static String getTenant(HttpServletRequest request) {
        var domain = request.getServerName();
        var dotIndex = domain.indexOf(".");

        String tenant = null;
        if (dotIndex != -1) {
            tenant = domain.substring(0, dotIndex);
        }

        return tenant;
    }
}
