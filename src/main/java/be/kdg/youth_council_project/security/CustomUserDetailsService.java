package be.kdg.youth_council_project.security;


import be.kdg.youth_council_project.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.info("CustomUserDetailsService is running loadUserByUsername with username {}", username);
        var user = userService.getUserByNameAndYouthCouncilId(username,1);
// youth council id must be hardcoded for now
        if (user != null) {
            var role = userService.getUserRoleOfMembership(user.getId(), 1);
            var authorities = new ArrayList<SimpleGrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
            LOGGER.debug("CustomUserDetailsService is returning user {}", user.getUsername());
            return new CustomUserDetails(user.getUsername(), user.getPassword(), authorities, user.getId());
        }
        LOGGER.debug("CustomUserDetailsService could not find user");
        throw new UsernameNotFoundException("User '" + username + "' doesn't exist");
    }
}

//retrieve user for a specific youth council - find some way to pass the youth council
// hard code youth council id 1 - searching for username, always pass in same youth council id as hardcoded
// ex. userservice.getUserByIdAndYouthCouncilID