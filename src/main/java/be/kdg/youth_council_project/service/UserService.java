package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;

public interface UserService {

    public User getUserByName(String username);
}
