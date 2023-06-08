package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;

import java.util.List;

public interface UserService {

    User saveUser(User user, long youthCouncilId);

    boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);

    List<User> getAdminsByYouthCouncilId(long youthCouncilId);

    List<Membership> findAdminsOfYouthCouncilByYouthCouncilId(long youthCouncilId);

    List<Membership> findMembershipsByUserId(long userId);

    Membership findMemberShipByUserIdAndYouthCouncilId(long userId, long youthCouncilId);

    void addAdminToYouthCouncil(long youthCouncilId, String email);

    User getUserById(long userId);

    List<User> getAllUsers();

    List<User> getAllNonDeletedUsersForYouthCouncil(long youthCouncilId);

    void deleteUser(long userId, long youthCouncilId);

    void updatePassword(long userId, String newPassword) throws Exception;

    User getUserByUsername(String userName);

    void updateUserRole(long userId, Role role, long tenantId);

    User getUserByEmail(String email);

    void createYCAdmin(User existingUser);
}
