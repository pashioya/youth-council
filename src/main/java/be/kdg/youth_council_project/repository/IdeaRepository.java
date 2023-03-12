package be.kdg.youth_council_project.repository;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    @Query(value="SELECT * FROM IDEA i WHERE i.YOUTH_COUNCIL_ID = ?1", nativeQuery=true)
    public List<Idea> findByYouthCouncilId(long youthCouncilId);


    @Query(value="SELECT * FROM IDEA i WHERE i.YOUTH_COUNCIL_ID = ?1 AND i.AUTHOR_ID = ?2", nativeQuery=true)
    public List<Idea> findByYouthCouncilIdAndUserId(long youthCouncilId, long userId);

}
