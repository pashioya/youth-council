package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * from app_user a JOIN membership m on a.user_id=m.user_id WHERE a.username = ?1 AND m.youth_council_id = ?2", nativeQuery = true)
    public User findByUsernameAndYouthCouncilId(String username, long youthCouncilId);


}
