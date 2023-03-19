package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;

public interface UserService {

    public User getUserByNameAndYouthCouncilId(String username, long youthCouncilId);

    public Role getUserRoleOfMembership(long userId, long youthCouncilId);

    public boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);
}
