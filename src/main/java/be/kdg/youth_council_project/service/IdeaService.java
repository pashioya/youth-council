package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.like.IdeaLike;

import java.util.List;

public interface IdeaService {

    public void setAuthorOfIdea(Idea idea, long userId);


    public void setThemeOfIdea(Idea idea, long themeId);

    public void setYouthCouncilOfIdea(Idea idea, long youthCouncilId);

    public Idea createIdea(Idea idea);

    public List<Idea> getIdeasOfYouthCouncil(long youthCouncilId);

    public List<Idea> getIdeasOfYouthCouncilAndUser(long youthCouncilId, long userId);

    public void setIdeaOfIdeaLike(IdeaLike ideaLike, long ideaId);

    public void setUserOfIdeaLike(IdeaLike ideaLike, long userId);

    public IdeaLike createIdeaLike(IdeaLike ideaLike);
}
