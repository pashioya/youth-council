package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.IdeaDto;
import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.service.IdeaService;
import be.kdg.youth_council_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/youth-councils/{id}/users")
public class UsersController {


    private final IdeaService ideaService;

    private final UserService userService;

    public UsersController(IdeaService ideaService, UserService userService) {
        this.ideaService = ideaService;
        this.userService = userService;
    }

    @GetMapping("{userId}/ideas")
    public ResponseEntity<List<IdeaDto>> getIdeasOfUser(@PathVariable("id") long youthCouncilId, @PathVariable("userId") long userId) {
        var ideas = ideaService.getIdeasByYouthCouncilIdAndUserId(youthCouncilId, userId);
        if (ideas.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    ideas.stream().map(
                            idea -> new IdeaDto(
                                    idea.getId(),
                                    idea.getDescription(),
                                  ideaService.getImagesOfIdea(idea.getId()),
                                    idea.getCreatedDate(),
                                    new UserDto(
                                            idea.getAuthor().getId(),
                                            idea.getAuthor().getFirstName()
                                    ),
                                    new ThemeDto(
                                            idea.getTheme().getId(),
                                            idea.getTheme().getName()
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
