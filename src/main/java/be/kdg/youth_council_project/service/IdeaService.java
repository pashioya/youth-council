package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.controller.mvc.viewmodels.IdeaViewModel;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;

import java.util.List;

public interface IdeaService {

    public void setAuthorOfIdea(Idea idea, long userId);


    public void setThemeOfIdea(Idea idea, long themeId);

    public void setYouthCouncilOfIdea(Idea idea, long youthCouncilId);

    public Idea createIdea(Idea idea);

    public List<Idea> getIdeasByYouthCouncilId(long youthCouncilId);

    public List<Idea> getIdeasByYouthCouncilIdAndUserId(long youthCouncilId, long userId);

    public void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId, long tenantId);

    public void setUserOfIdeaLike(IdeaLike ideaLike, long userId);

    public IdeaLike createIdeaLike(IdeaLike ideaLike);

    public boolean userAndIdeaInSameYouthCouncil(long userId, long ideaId, long youthCouncilId);

    public List<String> getImagesOfIdea(long ideaId);

    public Idea getIdeaById(long youthCouncilId, long ideaId);

    void setAuthorOfIdeaComment(IdeaComment ideaComment, long userId);

    void setIdeaOfIdeaComment(IdeaComment ideaComment, long ideaId);

    IdeaComment createIdeaComment(IdeaComment ideaComment);

    IdeaViewModel mapToIdeaViewModel(Idea idea);
}
