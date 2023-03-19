package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final MembershipRepository membershipRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository, MembershipRepository membershipRepository) {
        this.userRepository = userRepository;
        this.membershipRepository = membershipRepository;
    }

    public User getUserByNameAndYouthCouncilId(String username, long youthCouncilId) {
        LOGGER.info("UserService is running getUserByNameAndYouthCouncilId");
        User user = userRepository.findByUsernameAndYouthCouncilId(username, youthCouncilId);
        if (user != null) {
            LOGGER.debug("Returning user with username {}", user.getUsername());
        } else {
            LOGGER.debug("User with name {} not found", username);
        }
        return user;
    }

    public Role getUserRoleOfMembership(long userId, long youthCouncilId){
        LOGGER.info("UserService is running getUserRoleOfMembership");
        Role role = membershipRepository.findRoleByUserIdAndYouthCouncilId(userId, youthCouncilId);
        LOGGER.debug("UserService is returning role {}", role);
        return role;
    }

    public boolean userBelongsToYouthCouncil(long userId, long youthCouncilId){
        return membershipRepository.userIsMemberOfYouthCouncil(userId, youthCouncilId);
    }
}
