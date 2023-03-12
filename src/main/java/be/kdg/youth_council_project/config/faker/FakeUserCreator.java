package be.kdg.youth_council_project.config.faker;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class FakeUserCreator {
    private final Faker faker;
    private final UserRepository userRepository;

    public void createFakeUsers(int amount) {
        for (int i = 0; i < amount; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail("user" + i + "@gmail.com");
            user.setPassword("123");
            user.setRole("ROLE_USER");
            user.setPostCode(faker.address().zipCode());
            userRepository.save(user);
        }
    }

}
