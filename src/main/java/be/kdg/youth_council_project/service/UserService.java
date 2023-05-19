package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.User;

import java.util.List;

public interface UserService {

    User saveUser(User user, long youthCouncilId);

    boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);

    List<User> getAdminsByYouthCouncilId(long youthCouncilId);

    List<Membership> findAdminsOfYouthCouncilByYouthCouncilId(long youthCouncilId);

    void addAdminToYouthCouncil(long youthCouncilId, String email);

    User getUserById(long userId);

    List<User> getAllUsers();

    void deleteUser(long userId, long tenantId);

    void updatePassword(long userId, String newPassword) throws Exception;
}
