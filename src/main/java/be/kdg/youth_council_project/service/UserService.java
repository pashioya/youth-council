package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.User;

import java.util.List;

public interface UserService {

    User saveUser(User user, long youthCouncilId);

    User getUserByNameAndYouthCouncilId(String username, long youthCouncilId);

    boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);

    List<Membership> getMembersByYouthCouncilId(long youthCouncilId);

    List<User> getAllUsers();
}
