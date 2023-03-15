package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.like.IdeaLike;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.IdeaService;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/youth-councils/{id}/ideas")
public class IdeasController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final IdeaService ideaService;


    @PostMapping
    public ResponseEntity<IdeaDto> submitIdea(@PathVariable("id") long youthCouncilId,
                                              @RequestBody @Valid NewIdeaDto newIdeaDto,
                                              @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running submitIdea");
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
                                createdIdea.getTheme().getName()
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
        LOGGER.info("IdeasController is running getIdeas");
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


    // Can NewIdeaLikeDto be empty?
    @PostMapping("{ideaId}/likes")
    public ResponseEntity<IdeaLikeDto> likeIdea(@PathVariable("id") long youthCouncilId,
                                                @PathVariable("ideaId") long ideaId,
                                                @RequestBody NewIdeaLikeDto newIdeaLikeDto,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running likeIdea");
        IdeaLike createdIdeaLike = new IdeaLike();
        if (ideaService.userAndIdeaInSameYouthCouncil(user.getUserId(), ideaId, youthCouncilId)) {
            ideaService.setIdeaOfIdeaLike(createdIdeaLike, ideaId);
            ideaService.setUserOfIdeaLike(createdIdeaLike, user.getUserId());
            ideaService.createIdeaLike(createdIdeaLike);
            return new ResponseEntity<>(
                    new IdeaLikeDto(
                            new IdeaDto(
                                    createdIdeaLike.getIdeaLikeId().getIdea().getId(),
                                    createdIdeaLike.getIdeaLikeId().getIdea().getDescription(),
                                    createdIdeaLike.getIdeaLikeId().getIdea().getImages(),
                                    createdIdeaLike.getIdeaLikeId().getIdea().getCreatedDate(),
                                    new UserDto(
                                            createdIdeaLike.getIdeaLikeId().getIdea().getAuthor().getId(),
                                            createdIdeaLike.getIdeaLikeId().getIdea().getAuthor().getFirstName()
                                    ),
                                    new ThemeDto(
                                            createdIdeaLike.getIdeaLikeId().getIdea().getTheme().getId(),
                                            createdIdeaLike.getIdeaLikeId().getIdea().getTheme().getName()
                                    ),
                                    new YouthCouncilDto(
                                            createdIdeaLike.getIdeaLikeId().getIdea().getYouthCouncil().getId(),
                                            createdIdeaLike.getIdeaLikeId().getIdea().getYouthCouncil().getName(),
                                            createdIdeaLike.getIdeaLikeId().getIdea().getYouthCouncil().getMunicipalityName())),
                            new UserDto(
                                    createdIdeaLike.getIdeaLikeId().getLikedBy().getId(),
                                    createdIdeaLike.getIdeaLikeId().getLikedBy().getFirstName()
                            )
                    ),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
