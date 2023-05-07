package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.MembershipId;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user, long youthCouncilId) {
        LOGGER.info("UserService is running saveUser");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        MembershipId membershipId = new MembershipId(youthCouncilRepository.getReferenceById(youthCouncilId), savedUser);
        Membership membership = new Membership(membershipId, Role.USER, LocalDateTime.now());
        membershipRepository.save(membership);
        LOGGER.debug("User with name {} saved", savedUser.getUsername());
        return savedUser;
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
        return membershipRepository.findMembersOfYouthCouncilByYouthCouncilId(youthCouncilId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean userExists(long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean updatePassword(long userId, String newPassword) {
        var password = userRepository.findById(userId).orElse(null);
        if (password == null) {
            return false;
        }
        password.setPassword(newPassword);
        userRepository.save(password);
        return true;
    }

    @Override
    public List<User> getAdminsByYouthCouncilId(long youthCouncilId) {
        List<Membership> adminsMembershipData =
                membershipRepository.findAdminsOfYouthCouncilByYouthCouncilId(youthCouncilId);
        List<User> admins = adminsMembershipData.stream().map(membership -> membership.getMembershipId().getUser()).toList();
        LOGGER.debug("Returning {} admins", admins.size());
        return admins;
    }

    @Override
    public List<Membership> findAdminsOfYouthCouncilByYouthCouncilId(long youthCouncilId) {
        return membershipRepository.findAdminsOfYouthCouncilByYouthCouncilId(youthCouncilId);
    }

    @Override
    public void addAdminToYouthCouncil(long youthCouncilId, String email) {
        LOGGER.info("UserService is running addAdminToYouthCouncil");
        User user = userRepository.findByEmail(email).orElse(null);
        if(user==null){
            user = new User();
            user.setEmail(email);
        }
        userRepository.save(user);
        MembershipId membershipId = new MembershipId(youthCouncilRepository.getReferenceById(youthCouncilId), user);
        Membership membership = new Membership(membershipId, Role.YOUTH_COUNCIL_ADMINISTRATOR, LocalDateTime.now());
        LOGGER.debug("Admin with email {} added to youth council with id {}", email, youthCouncilId);
        membershipRepository.save(membership);
    }

//    @Override
//    public List<User> getAdminsByYouthCouncilId(long youthCouncilId) {
//        LOGGER.info("UserService is running getAdminsByYouthCouncilId");
//        List<User> admins = userRepository.findUsersByRoleAndYouthCouncilId(Role.YOUTH_COUNCIL_ADMINISTRATOR, youthCouncilId);
//        LOGGER.debug("Returning {} admins", admins.size());
//        return admins;
//    }
}
