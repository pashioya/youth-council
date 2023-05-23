package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query(value = "SELECT * FROM app_user AS a"
            + " WHERE a.is_general_admin = true"
            + " AND a.username = ?1", nativeQuery = true)
    Optional<User> findGeneralAdmin(String username);

    @Query(value = "SELECT * from app_user a " +
            "JOIN membership m on a.user_id=m.user_id " +
            "WHERE a.user_id = ?1 AND m.youth_council_id = ?2", nativeQuery = true)
    Optional<User> findByIdAndYouthCouncilId(long userId, long youthCouncilId);

    @Query(value = "SELECT * from app_user a " +
            "JOIN membership m on a.user_id=m.user_id " +
            "WHERE m.youth_council_id = ?2 and m.role=?1", nativeQuery = true)
    List<User> findUsersByRoleAndYouthCouncilId(Role youthCouncilAdministrator, long youthCouncilId);

    Optional<User> findByEmail(String email);
}
