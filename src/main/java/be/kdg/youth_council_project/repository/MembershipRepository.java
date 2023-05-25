package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    @Query(value = "SELECT * FROM membership m JOIN youth_council y ON m.youth_council_id=y.id WHERE m.user_id = ?1 AND LOWER(y.slug) = LOWER(?2)", nativeQuery = true)
    Optional<Membership> findByUserIdAndSlug(long userId, String slug);

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM membership m WHERE m.user_id =?1 AND youth_council_id =?2", nativeQuery = true)
    boolean userIsMemberOfYouthCouncil(long userId, long youthCouncilId);

    @Query(value = "SELECT * FROM membership m WHERE m.youth_council_id =?1", nativeQuery = true)
    List<Membership> findMembersOfYouthCouncilByYouthCouncilId(long youthCouncilId);

    @Query(value = "SELECT * FROM membership m WHERE m.youth_council_id =?1 AND m.role = 'YOUTH_COUNCIL_ADMINISTRATOR'", nativeQuery = true)
    List<Membership> findAdminsOfYouthCouncilByYouthCouncilId(long youthCouncilId);

    @Query(value = "SELECT * FROM membership m WHERE m.youth_council_id =?2 AND m.user_id =?1", nativeQuery = true)
    Membership findByUserIdAndYouthCouncilId(long userId, long youthCouncilId);

    @Query(value = "SELECT * FROM membership m WHERE m.user_id =?1", nativeQuery = true)
    List<Membership> findMembershipsByUserId(long userId);
}
