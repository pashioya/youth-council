package be.kdg.youth_council_project.config;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final YouthCouncilRepository youthCouncilRepository;
    private final UserRepository userRepository;

    private final Logger logger = Logger.getLogger(DatabaseSeeder.class.getName());

    @Override
    public void run(String... args) throws Exception {

        logger.info("Seeding database...");

    }
}
