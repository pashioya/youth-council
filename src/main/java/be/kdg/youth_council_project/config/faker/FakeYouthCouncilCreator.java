package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.style.Style;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.SocialMediaLink;
import be.kdg.youth_council_project.repository.*;
import be.kdg.youth_council_project.util.RandomUtil;
import be.kdg.youth_council_project.util.WebPage;
import be.kdg.youth_council_project.util.WebPageBuilder;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FakeYouthCouncilCreator {
    private final YouthCouncilRepository youthCouncilRepository;
    private final WebPageRepository webPageRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final MunicipalityRepository municipalityRepository;

    private final Faker faker;

    private final List<YouthCouncil> youthCouncils;

    public List<YouthCouncil> createFakeYouthCouncils(int amount) {
        for (int i = 0; i < amount; i++) {
            YouthCouncil youthCouncil = new YouthCouncil();
            youthCouncil.setName(faker.job().keySkills());
            youthCouncil.setLogo(faker.avatar().image());
            WebPage webPage = new WebPageBuilder().withEverything().build();
            webPageRepository.save(webPage);
            youthCouncil.setHomePage(webPage);
            youthCouncil.setSocialMediaLinks(null);
            youthCouncil.setActivities(null);
            youthCouncil.setIdeas(null);
            Membership membership = new Membership();
            membership.setUser(userRepository.findAll().get(0));
            youthCouncil.setMemberships(List.of(membership));
            youthCouncil.setQuestionnaire(null);
            Municipality municipality = new Municipality();
            municipality.setName(faker.job().keySkills());
            municipality.setPostCodes(List.of(RandomUtil.getRandomInt(1000, 9999), RandomUtil.getRandomInt(1000, 9999)));
            youthCouncil.setMunicipality(municipality);

            municipalityRepository.save(municipality);
            membershipRepository.save(membership);
            youthCouncils.add(youthCouncil);
            youthCouncilRepository.save(youthCouncil);
        }
        return youthCouncils;
    }
}
