package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user, long youthCouncilId);

    User getUserByNameAndYouthCouncilId(String username, long youthCouncilId);

    boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);

    List<Membership> getMembersByYouthCouncilId(long youthCouncilId);
    List<User> getAdminsByYouthCouncilId(long youthCouncilId);

    List<Membership> findAdminsOfYouthCouncilByYouthCouncilId(long youthCouncilId);

    void addAdminToYouthCouncil(long youthCouncilId, String email);
    User getUserById(long userId);
    List<User> getAllUsers();
    boolean userExists(long userId);
    void deleteUser(long userId, long tenantId, long ideaId);
    boolean updatePassword(long userId, String newPassword);
}
