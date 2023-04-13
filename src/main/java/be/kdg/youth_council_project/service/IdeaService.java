package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.IdeaImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IdeaService {

    public void setAuthorOfIdea(Idea idea, long userId, long youthCouncilId);


    public void setThemeOfIdea(Idea idea, long themeId);

    public void setYouthCouncilOfIdea(Idea idea, long youthCouncilId);

    public Idea createIdea(Idea idea);

    public List<Idea> getIdeasByYouthCouncilId(long youthCouncilId);

    public List<Idea> getIdeasByYouthCouncilIdAndUserId(long youthCouncilId, long userId);

    public void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId, long youthCouncilId);

    public void setUserOfIdeaLike(IdeaLike ideaLike, long userId, long youthCouncilId);

    public boolean createIdeaLike(IdeaLike ideaLike);

    public List<IdeaImage> getImagesOfIdea(long ideaId);

    public Idea getIdeaById(long youthCouncilId, long ideaId);

    void setAuthorOfIdeaComment(IdeaComment ideaComment, long userId, long youthCouncilId);

    void setIdeaOfIdeaComment(IdeaComment ideaComment, long ideaId, long youthCouncilId);

    IdeaComment createIdeaComment(IdeaComment ideaComment);

    void removeIdeaLike(long actionPointId, long userId, long tenantId);

    void removeIdea(long ideaId, long youthCouncilId);

    void addImageToIdea(Idea createdIdea, MultipartFile image);

    boolean isLikedByUser(Long id, long userId);
}
