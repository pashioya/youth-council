package be.kdg.youth_council_project.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
public class CustomUserDetails extends User {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final long userId;
    private final Long tenantId;

    public CustomUserDetails(String username, String password, long userId, Long tenantId,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
        this.tenantId = tenantId;
    }

    public long getUserId() {
        return userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    boolean hasRole(String role){
        LOGGER.info("CustomUserDetails is running hasRole");
        boolean hasRole = getAuthorities().contains(new SimpleGrantedAuthority(role));
        LOGGER.debug("User {} has role {}: {}", this, role, hasRole);
        return hasRole;
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "userId=" + userId +
                ", tenantId=" + tenantId +
                '}';
    }
}
