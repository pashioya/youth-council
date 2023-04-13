package be.kdg.youth_council_project.security;


import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.tenants.TenantContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;

    public CustomUserDetailsService(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("CustomUserDetailsService is running loadUserByUsername with username {}", username);
        var tenant = TenantContext.getCurrentTenant();

        if (tenant != null) {
            return loadUser(username, tenant);
        } else {
            return loadGeneralAdmin(username);
        }
    }

    private UserDetails loadUser(String username, String tenant) {
        LOGGER.info("CustomUserDetailsService is running loadUser with username {}", username);
        var user = userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException(
                                        "'" + username + "'" +
                                                "' was not found."));
        LOGGER.debug("Returned user {}", user);
        var membership = membershipRepository.findByUserIdAndSlug(user.getId(), tenant).orElseThrow(() -> new UsernameNotFoundException(
                "'" + username + "'" +
                        " is not a member of tenant " + "'" + tenant + "'"));
        LOGGER.debug("Returned membership {}", membership);
        var auths = new ArrayList<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(membership.getRole().getCode()));
        return new CustomUserDetails(user.getUsername(), user.getPassword(), user.getId(),
                membership.getMembershipId().getYouthCouncil().getId(), auths);
    }

    private UserDetails loadGeneralAdmin(String username) {
        var admin = userRepository.findGeneralAdmin(username).orElseThrow(
                () -> new UsernameNotFoundException(
                        "'" + username + "' was not found as a general admin."));
        var auths = new ArrayList<GrantedAuthority>();
        auths.add(new SimpleGrantedAuthority(Role.GENERAL_ADMINISTRATOR.getCode()));
        return new CustomUserDetails(admin.getEmail(), admin.getPassword(), admin.getId(), null,
                auths);
    }

}

//retrieve user for a specific youth council - find some way to pass the youth council
// hard code youth council id 1 - searching for username, always pass in same youth council id as hardcoded
// ex. userservice.getUserByIdAndYouthCouncilID