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
        user.setDateCreated(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        MembershipId membershipId = new MembershipId(youthCouncilRepository.getReferenceById(youthCouncilId), savedUser);
        Membership membership = new Membership(membershipId, Role.USER, LocalDateTime.now());
        membershipRepository.save(membership);
        LOGGER.debug("User with name {} saved", savedUser.getUsername());
        return savedUser;
    }


    public boolean userBelongsToYouthCouncil(long userId, long youthCouncilId) {
        return membershipRepository.userIsMemberOfYouthCouncil(userId, youthCouncilId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllNonDeletedUsersForYouthCouncil(long youthCouncilId) {
        List<Membership> usersMembershipData =
                membershipRepository.findMembersOfYouthCouncilByYouthCouncilId(youthCouncilId);
        List<User> users = new java.util.ArrayList<>(usersMembershipData.stream().map(membership -> membership.getMembershipId().getUser()).toList());
        users.removeIf(
                user -> membershipRepository.findByUserIdAndYouthCouncilId(user.getId(), youthCouncilId).getRole().equals(Role.DELETED)
        );
        return users;
    }

    @Override
    public void deleteUser(long userId, long youthCouncilId) {
        Membership membership = membershipRepository.findByUserIdAndYouthCouncilId(userId, youthCouncilId);
        membership.setRole(Role.DELETED);
        membershipRepository.save(membership);
    }

    @Override
    public void updatePassword(long userId, String newPassword) throws Exception {
        if (newPassword == null || newPassword.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
//        TODO: Create Custom Exception
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String userName) {
        LOGGER.info("UserService is running getUserByUsername");
        return userRepository.findByUsername(userName).orElse(null);
    }

    @Override
    public void updateUserRole(long userId, Role role, long tenantId) {
        LOGGER.info("UserService is running updateUserRole");
        Membership membership = membershipRepository.findByUserIdAndYouthCouncilId(userId, tenantId);
        membership.setRole(role);
        membershipRepository.save(membership);
    }

    @Override
    public User getUserByEmail(String email) {
        LOGGER.info("UserService is running getUserByEmail");
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void createYCAdmin(User existingUser) {
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));
        userRepository.save(existingUser);
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
    public List<Membership> findMembershipsByUserId(long userId) {
        return membershipRepository.findMembershipsByUserId(userId);
    }

    @Override
    public Membership findMemberShipByUserIdAndYouthCouncilId(long userId, long youthCouncilId) {
        return membershipRepository.findByUserIdAndYouthCouncilId(userId, youthCouncilId);
    }

    @Override
    public void addAdminToYouthCouncil(long youthCouncilId, String email) {
        LOGGER.info("UserService is running addAdminToYouthCouncil");
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User();
            user.setEmail(email);
        }
        userRepository.save(user);
        MembershipId membershipId = new MembershipId(youthCouncilRepository.getReferenceById(youthCouncilId), user);
        Membership membership = new Membership(membershipId, Role.YOUTH_COUNCIL_ADMINISTRATOR, LocalDateTime.now());
        LOGGER.debug("Admin with email {} added to youth council with id {}", email, youthCouncilId);
        membershipRepository.save(membership);
    }

    @Override
    public User getUserById(long userId) {
        LOGGER.info("UserService is running getUserById");
        return userRepository.findById(userId).orElse(null);
    }


}
