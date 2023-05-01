package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user, long youthCouncilId);

    User getUserByNameAndYouthCouncilId(String username, long youthCouncilId);

    List<User> getAllUsers();

    boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);

    List<Membership> getMembersByYouthCouncilId(long youthCouncilId);
    User getUser(long userId);
    boolean userExists(long userId);
    void deleteUser(long userId);
    boolean changePassword(long userId, String password);
}
