package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.service.IdeaService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/youth-councils/{id}/ideas")
public class IdeasController {

    private final IdeaService ideaService;

    public IdeasController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }


    @PostMapping
    public ResponseEntity<IdeaDto> submitIdea(@PathVariable("id") long youthCouncilId,
                                              @RequestBody @Valid NewIdeaDto newIdeaDto) {
        Idea createdIdea = new Idea(newIdeaDto.getDescription(), newIdeaDto.getImages());
        ideaService.setAuthorOfIdea(createdIdea, newIdeaDto.getAuthorId());
        ideaService.setThemeOfIdea(createdIdea, newIdeaDto.getThemeId());
        ideaService.setYouthCouncilOfIdea(createdIdea, youthCouncilId);
        ideaService.createIdea(createdIdea);
        return new ResponseEntity<>(
                new IdeaDto(
                        createdIdea.getId(),
                        createdIdea.getDescription(),
                        createdIdea.getImages(),
                        createdIdea.getDateAdded(),
                        createdIdea.getLikes(),
                        new UserDto(
                                createdIdea.getAuthor().getId(),
                                createdIdea.getAuthor().getFirstName()
                        ),
                        new ThemeDto(
                                createdIdea.getTheme().getId(),
                                createdIdea.getDescription()
                        ),
                        new YouthCouncilDto(
                                createdIdea.getYouthCouncil().getId(),
                                createdIdea.getYouthCouncil().getName(),
                                createdIdea.getYouthCouncil().getMunicipalityName())
                ),
                HttpStatus.CREATED);
    }




    @GetMapping()
    public ResponseEntity<List<IdeaDto>> getIdeas(@PathVariable("id") long youthCouncilId) {
        var ideas = ideaService.getIdeasOfYouthCouncil(youthCouncilId);
        if (ideas.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    ideas.stream().map(
                            idea -> new IdeaDto(
                                    idea.getId(),
                                    idea.getDescription(),
                                    idea.getImages(),
                                    idea.getDateAdded(),
                                    idea.getLikes(),
                                    new UserDto(
                                            idea.getAuthor().getId(),
                                            idea.getAuthor().getFirstName()
                                    ),
                                    new ThemeDto(
                                            idea.getTheme().getId(),
                                            idea.getDescription()
                                    ),
                                    new YouthCouncilDto(
                                            idea.getYouthCouncil().getId(),
                                            idea.getYouthCouncil().getName(),
                                            idea.getYouthCouncil().getMunicipalityName())
                            )).toList()
                    , HttpStatus.OK);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<IdeaDto>> getIdeasOfUser(@PathVariable("id") long youthCouncilId, @PathVariable("userId") long userId) {
        var ideas = ideaService.getIdeasOfYouthCouncilAndUser(youthCouncilId, userId);
        if (ideas.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    ideas.stream().map(
                            idea -> new IdeaDto(
                                    idea.getId(),
                                    idea.getDescription(),
                                    idea.getImages(),
                                    idea.getDateAdded(),
                                    idea.getLikes(),
                                    new UserDto(
                                            idea.getAuthor().getId(),
                                            idea.getAuthor().getFirstName()
                                    ),
                                    new ThemeDto(
                                            idea.getTheme().getId(),
                                            idea.getDescription()
                                    ),
                                    new YouthCouncilDto(
                                            idea.getYouthCouncil().getId(),
                                            idea.getYouthCouncil().getName(),
                                            idea.getYouthCouncil().getMunicipalityName())
                            )).toList()
                    , HttpStatus.OK);
        }
    }

}
