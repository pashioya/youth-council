package be.kdg.youth_council_project.tenants;


import static org.springframework.http.HttpStatus.FORBIDDEN;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

import be.kdg.youth_council_project.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class TenantAuthorizationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER =
            Logger.getLogger(TenantAuthorizationFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        var tenantId = TenantContext.getCurrentTenantId();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = authentication == null ? null : (CustomUserDetails) authentication.getPrincipal();
        var userTenantId = user == null ? null : user.getTenantId();

        if (user == null || Objects.equals(tenantId, userTenantId)) {
            chain.doFilter(request, response);
        } else {
            LOGGER.warning("Attempted cross-tenant access. User ID '" + user.getUserId() +
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
