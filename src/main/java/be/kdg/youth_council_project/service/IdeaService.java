package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;

import java.util.List;

public interface IdeaService {

    public boolean setAuthorOfIdea(Idea idea, long userId);


    public boolean setThemeOfIdea(Idea idea, long themeId);

    public boolean setYouthCouncilOfIdea(Idea idea, long youthCouncilId);

    public Idea createIdea(Idea idea);

    public List<Idea> getIdeasOfYouthCouncil(long youthCouncilId);

    public List<Idea> getIdeasOfYouthCouncilAndUser(long youthCouncilId, long userId);
}
