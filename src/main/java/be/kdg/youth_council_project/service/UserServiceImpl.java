package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.MembershipId;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.repository.idea.IdeaRepository;
import be.kdg.youth_council_project.repository.news_item.NewsItemRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final IdeaRepository ideaRepository;
    private final NewsItemRepository newsItem;
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
    @Transactional
    public void deleteUser(long userId) {
        List<Idea> ideas = ideaRepository.findByAuthor(userId);
        for(Idea idea : ideas){
            ideaRepository.deleteActionPointLinksById(idea.getId());
            ideaRepository.delete(idea);
        }
        membershipRepository.deleteByUserId(userId);
        newsItem.deleteByAuthorId(userId);
        userRepository.deleteById(userId);
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
    public List<User> getAllUsersByYouthCouncilId(long tenantId) {
        List<Membership> usersMembershipData =
                membershipRepository.findMembersOfYouthCouncilByYouthCouncilId(tenantId);
        List<User> users = usersMembershipData.stream().map(membership -> membership.getMembershipId().getUser()).toList();
        LOGGER.debug("Returning {} users", users.size());
        return users;
    }

    @Override
    public User getUserByUsername(String userName) {
        LOGGER.info("UserService is running getUserByUsername");
        return userRepository.findByUsername(userName).orElse(null);
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
