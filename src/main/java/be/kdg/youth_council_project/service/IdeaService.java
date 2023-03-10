package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;

public interface IdeaService {

    public boolean setAuthorOfIdea(Idea idea, long userId);


    public boolean setThemeOfIdea(Idea idea, long themeId);

    public boolean setYouthCouncilOfIdea(Idea idea, int youthCouncilId);

    public Idea createIdea(Idea idea);
}
