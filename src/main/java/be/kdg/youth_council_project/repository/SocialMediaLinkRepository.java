package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youth_council_items.SocialMediaLink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialMediaLinkRepository extends JpaRepository<SocialMediaLink, Long> {
    @Query("SELECT s FROM SocialMediaLink s WHERE s.youthCouncil.id = ?1")
    List<SocialMediaLink> findAllByYouthCouncilId(long tenantId);
}
