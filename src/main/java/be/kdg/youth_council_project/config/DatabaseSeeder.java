package be.kdg.youth_council_project.config;

import be.kdg.youth_council_project.config.faker.FakeDataCreator;
import be.kdg.youth_council_project.domain.platform.*;
import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Activity;
import be.kdg.youth_council_project.repository.MunicipalityRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.WebPageRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.util.WebPage;
import be.kdg.youth_council_project.util.WebPageBuilder;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final YouthCouncilRepository youthCouncilRepository;
    private final WebPageRepository webPageRepository;
    private final UserRepository userRepository;
    private final MunicipalityRepository municipalityRepository;

    private final FakeDataCreator fakeDataCreator;

    private final Logger logger = Logger.getLogger(DatabaseSeeder.class.getName());

    @Override
    public void run(String... args) throws Exception {


        // Create fake data
        fakeDataCreator.createFakeUsers(10);

        fakeDataCreator.createFakeYouthCouncils(1);

        fakeDataCreator.createFakeThemes(10);
        fakeDataCreator.createFakeIdeas(10);
        fakeDataCreator.createFakeActivities(10);


    }
}
