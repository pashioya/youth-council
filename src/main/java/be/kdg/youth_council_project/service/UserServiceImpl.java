package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

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


    public boolean userBelongsToYouthCouncil(long userId, long youthCouncilId) {
        return membershipRepository.userIsMemberOfYouthCouncil(userId, youthCouncilId);
    }

    @Override
    public List<Membership> getMembersByYouthCouncilId(long youthCouncilId) {
        List<Membership> members = membershipRepository.findMembersOfYouthCouncilByYouthCouncilId(youthCouncilId);
        return members;
    }

    @Override
    public List<User> getAdminsByYouthCouncilId(long youthCouncilId) {
        return null;
    }

//    @Override
//    public List<User> getAdminsByYouthCouncilId(long youthCouncilId) {
//        LOGGER.info("UserService is running getAdminsByYouthCouncilId");
//        List<User> admins = userRepository.findUsersByRoleAndYouthCouncilId(Role.YOUTH_COUNCIL_ADMINISTRATOR, youthCouncilId);
//        LOGGER.debug("Returning {} admins", admins.size());
//        return admins;
//    }


}
