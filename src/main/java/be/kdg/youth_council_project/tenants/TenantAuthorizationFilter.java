package be.kdg.youth_council_project.tenants;


import static org.springframework.http.HttpStatus.FORBIDDEN;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import be.kdg.youth_council_project.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class TenantAuthorizationFilter extends OncePerRequestFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        LOGGER.info("TenantAuthorizationFilter is running doFilterInternal");
        var tenantId = TenantContext.getCurrentTenantId();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("authentication: {}", authentication);
        var user = authentication == null ? null : (CustomUserDetails) authentication.getPrincipal();
        var userTenantId = user == null ? null : user.getTenantId();

        LOGGER.debug("TenantAuthorizationFilter found user {} with userTenantId {} who is making request to tenant with id {}",
                user, userTenantId, tenantId);
        if (user == null || Objects.equals(tenantId, userTenantId)) {
            chain.doFilter(request, response);
        } else {
            LOGGER.debug("Attempted cross-tenant access. User ID '" + user.getUserId() +
                    "', User's Tenant ID '" + user.getTenantId() + "', Target Tenant ID '" +
                    tenantId + "'.");
            response.setStatus(FORBIDDEN.value());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/webjars/")
                || request.getRequestURI().startsWith("/css/")
                || request.getRequestURI().startsWith("/js/")
                || request.getRequestURI().endsWith(".ico");
    }
}
