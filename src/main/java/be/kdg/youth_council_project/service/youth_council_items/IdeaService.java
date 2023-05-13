package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.IdeaImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdeaService {
    List<Idea> getAllIdeas();

    void setAuthorOfIdea(Idea idea, long userId, long youthCouncilId);

    void setThemeOfIdea(Idea idea, long themeId);

    void setYouthCouncilOfIdea(Idea idea, long youthCouncilId);

    Idea createIdea(Idea idea);

    List<Idea> getIdeasByYouthCouncilId(long youthCouncilId);

    List<Idea> getIdeasByYouthCouncilIdAndUserId(long youthCouncilId, long userId);

    void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId, long youthCouncilId);

    void setUserOfIdeaLike(IdeaLike ideaLike, long userId, long youthCouncilId);

    boolean createIdeaLike(IdeaLike ideaLike);

    List<IdeaImage> getImagesOfIdea(long ideaId);

    Idea getIdeaById(long youthCouncilId, long ideaId);

    void setAuthorOfIdeaComment(IdeaComment ideaComment, long userId, long youthCouncilId);

    void setIdeaOfIdeaComment(IdeaComment ideaComment, long ideaId, long youthCouncilId);

    IdeaComment createIdeaComment(IdeaComment ideaComment);

    void removeIdeaLike(long actionPointId, long userId, long tenantId);

    void removeIdea(long ideaId, long youthCouncilId);

    void addImageToIdea(Idea createdIdea, MultipartFile image);

    boolean isLikedByUser(Long id, long userId);

    List<Idea> getIdeasByUserId(long userId);

    List<IdeaComment> getCommentsByUserId(long userId);
    User findUserByAuthorId(long authorId);
}
