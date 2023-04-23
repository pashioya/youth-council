package be.kdg.youth_council_project.repository.idea;


import be.kdg.youth_council_project.domain.platform.youth_council_items.images.IdeaImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IdeaImageRepository extends JpaRepository<IdeaImage, Long> {

    @Query(value="SELECT * FROM idea_image ii WHERE ii.IDEA_ID=?1", nativeQuery = true)
    List<IdeaImage> getImagesByIdeaId(long ideaId);
}
