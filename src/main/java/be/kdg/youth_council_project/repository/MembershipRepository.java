package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {


    @Query(value="SELECT role FROM membership m WHERE m.user_id = ?1 AND m.youth_council_id = ?2", nativeQuery=true)
    public Role findRoleByUserIdAndYouthCouncilId(long userId, long youthCouncilId);


}
