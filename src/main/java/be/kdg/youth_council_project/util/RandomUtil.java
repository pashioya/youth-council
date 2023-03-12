package be.kdg.youth_council_project.util;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Random;
public class RandomUtil {
        private static final Random random = new Random();

        public static int getRandomInt(int min, int max) {
            return random.nextInt((max - min) + 1) + min;
        }

        public static double getRandomDouble(double min, double max) {
            return min + (max - min) * random.nextDouble();
        }
        public static Object getRandomEntity(JpaRepository repository){
                int count = (int) repository.count();
                if (count == 0) return null;
                int randomId = getRandomInt(1, count);
                return repository.findById((long) randomId).get();
        }
}
