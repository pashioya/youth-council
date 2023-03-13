//package be.kdg.youth_council_project.config;
//
//import be.kdg.youth_council_project.config.faker.FakeDataCreator;
//import be.kdg.youth_council_project.domain.platform.*;
//import be.kdg.youth_council_project.domain.platform.style.Style;
//import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Activity;
//import be.kdg.youth_council_project.repository.MunicipalityRepository;
//import be.kdg.youth_council_project.repository.UserRepository;
//import be.kdg.youth_council_project.repository.WebPageRepository;
//import be.kdg.youth_council_project.repository.YouthCouncilRepository;
//import be.kdg.youth_council_project.util.WebPage;
//import be.kdg.youth_council_project.util.WebPageBuilder;
//import com.github.javafaker.Faker;
//import lombok.AllArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import java.util.logging.Logger;
//
//@Component
//@AllArgsConstructor
//@Profile("dev")
//public class DatabaseSeeder implements CommandLineRunner {
//    private final YouthCouncilRepository youthCouncilRepository;
//    private final WebPageRepository webPageRepository;
//    private final UserRepository userRepository;
//    private final MunicipalityRepository municipalityRepository;
//
//    private final FakeDataCreator fakeDataCreator;
//
//    private final Logger logger = Logger.getLogger(DatabaseSeeder.class.getName());
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
//        // Create fake data
//        logger.info("Creating fake data");
//        var users = fakeDataCreator.createFakeUsers(10);
//        var yc = fakeDataCreator.createFakeYouthCouncils(1);
//        var themes = fakeDataCreator.createFakeThemes(10);
//        var ideas = fakeDataCreator.createFakeIdeas(10);
//        var activities = fakeDataCreator.createFakeActivities(10);
//        var questionnaires = fakeDataCreator.createFakeQuestionnaire();
//
//        // log all variables
//        logger.info("Users: " + users);
//        logger.info("Youth Councils: " + yc);
//        logger.info("Themes: " + themes);
//        logger.info("Ideas: " + ideas);
//        logger.info("Activities: " + activities);
//        logger.info("Questionnaires: " + questionnaires);
//
//
//
//    }
//}
