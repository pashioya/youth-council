package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;

import java.util.List;

public interface UserService {

    public User getUserByNameAndYouthCouncilId(String username, long youthCouncilId);


    public boolean userBelongsToYouthCouncil(long userId, long youthCouncilId);

}
