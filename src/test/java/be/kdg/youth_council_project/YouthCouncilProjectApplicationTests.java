package be.kdg.youth_council_project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class YouthCouncilProjectApplicationTests {
    @Test
    void contextLoads() {
    }

    @Test
    void OneAndOneEqualsTwo() {
        int result = 1 + 1;
        assertEquals(2, result);
    }
}
