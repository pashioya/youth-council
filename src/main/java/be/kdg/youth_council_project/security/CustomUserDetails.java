package be.kdg.youth_council_project.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
public class CustomUserDetails extends User {
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
}
