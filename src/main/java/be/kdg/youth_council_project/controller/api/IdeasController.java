package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.like.IdeaLike;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.IdeaService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                                              @RequestBody @Valid NewIdeaDto newIdeaDto,
                                              @AuthenticationPrincipal CustomUserDetails user) {
        Idea createdIdea = new Idea(newIdeaDto.getDescription(), newIdeaDto.getImages());
        ideaService.setAuthorOfIdea(createdIdea, user.getUserId());
        ideaService.setThemeOfIdea(createdIdea, newIdeaDto.getThemeId());
        ideaService.setYouthCouncilOfIdea(createdIdea, youthCouncilId);
        ideaService.createIdea(createdIdea);
        return new ResponseEntity<>(
                new IdeaDto(
                        createdIdea.getId(),
                        createdIdea.getDescription(),
                        createdIdea.getImages(),
                        createdIdea.getCreatedDate(),
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
                                    idea.getCreatedDate(),
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



    @PostMapping("{ideaId}/likes")
    public ResponseEntity<IdeaLikeDto> likeIdea(@PathVariable("id") long youthCouncilId,
                                              @RequestBody NewIdeaLikeDto newIdeaLikeDto,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        IdeaLike createdIdeaLike = new IdeaLike();
        ideaService.setIdeaOfIdeaLike(createdIdeaLike, newIdeaLikeDto.getIdeaId());
        ideaService.setUserOfIdeaLike(createdIdeaLike, user.getUserId());
        ideaService.createIdeaLike(createdIdeaLike);
        return new ResponseEntity<>(
                new IdeaLikeDto(
                        new IdeaDto(
                                createdIdeaLike.getIdea().getId(),
                                createdIdeaLike.getIdea().getDescription(),
                                createdIdeaLike.getIdea().getImages(),
                                createdIdeaLike.getIdea().getCreatedDate(),
                                new UserDto(
                                        createdIdeaLike.getIdea().getAuthor().getId(),
                                        createdIdeaLike.getIdea().getAuthor().getFirstName()
                                ),
                                new ThemeDto(
                                        createdIdeaLike.getIdea().getTheme().getId(),
                                        createdIdeaLike.getIdea().getDescription()
                                ),
                                new YouthCouncilDto(
                                        createdIdeaLike.getIdea().getYouthCouncil().getId(),
                                        createdIdeaLike.getIdea().getYouthCouncil().getName(),
                                        createdIdeaLike.getIdea().getYouthCouncil().getMunicipalityName())),
                        new UserDto(
                                createdIdeaLike.getLikedBy().getId(),
                                createdIdeaLike.getLikedBy().getFirstName()
                        )
                ),
                HttpStatus.CREATED);
    }

}
