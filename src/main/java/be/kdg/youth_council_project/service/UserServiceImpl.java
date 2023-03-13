package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByName(String username) {
        LOGGER.info("UserService is running getUserByName");
        User user = userRepository.findByUsername(username);
        if (user != null) {
            LOGGER.debug("Returning user with username {}", user.getEmail());
        } else {
            LOGGER.debug("User with name {} not found", username);
        }
        return user;

    }
}
