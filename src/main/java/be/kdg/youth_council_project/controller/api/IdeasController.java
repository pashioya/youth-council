package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLikeId;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.IdeaService;

import javax.validation.Valid;

import be.kdg.youth_council_project.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/youth-councils/{youthCouncilId}/ideas")
public class IdeasController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final IdeaService ideaService;

    private final UserService userService;


    @PostMapping
    public ResponseEntity<IdeaDto> addIdea(@PathVariable("youthCouncilId") long youthCouncilId,
                                           @RequestBody @Valid NewIdeaDto newIdeaDto,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running submitIdea");
        if (userService.userBelongsToYouthCouncil(user.getUserId(), youthCouncilId)) {
            Idea createdIdea = new Idea(newIdeaDto.getDescription(), newIdeaDto.getImages());
            ideaService.setAuthorOfIdea(createdIdea, user.getUserId());
            ideaService.setThemeOfIdea(createdIdea, newIdeaDto.getThemeId());
            ideaService.setYouthCouncilOfIdea(createdIdea, youthCouncilId);
            ideaService.createIdea(createdIdea);
            return new ResponseEntity<>(
                    new IdeaDto(
                            createdIdea.getId(),
                            createdIdea.getDescription(),
                            ideaService.getImagesOfIdea(createdIdea.getId()),
                            createdIdea.getCreatedDate(),
                            new UserDto(
                                    createdIdea.getAuthor().getId(),
                                    createdIdea.getAuthor().getUsername()
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
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/{ideaId}")
    public ResponseEntity<IdeaDto> getIdea(@PathVariable("youthCouncilId") long youthCouncilId,
                                           @PathVariable("ideaId") long ideaId) {
        LOGGER.info("IdeasController is running getIdea");
        var idea = ideaService.getIdeaById(youthCouncilId, ideaId);
        LOGGER.info("IdeasController found action point {}", idea);
        return new ResponseEntity<>(
                new IdeaDto(
                        idea.getId(),
                        idea.getDescription(),
                        ideaService.getImagesOfIdea(idea.getId()),
                        idea.getCreatedDate(),
                        new UserDto(
                                idea.getAuthor().getId(),
                                idea.getAuthor().getUsername()
                        ),
                        new ThemeDto(
                                idea.getTheme().getId(),
                                idea.getTheme().getName()
                        ),
                        new YouthCouncilDto(
                                idea.getYouthCouncil().getId(),
                                idea.getYouthCouncil().getName(),
                                idea.getYouthCouncil().getMunicipalityName())
                ),
                HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<IdeaDto>> getAllIdeas(@PathVariable("youthCouncilId") long youthCouncilId) {
        LOGGER.info("IdeasController is running getAllIdeas");
        var ideas = ideaService.getIdeasByYouthCouncilId(youthCouncilId);
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
                                            idea.getAuthor().getUsername()
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


    @PostMapping("/{ideaId}/likes")
    public ResponseEntity<IdeaLikeDto> likeIdea(@PathVariable("youthCouncilId") long youthCouncilId,
                                                @PathVariable("ideaId") long ideaId,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running likeIdea");

        if (ideaService.userAndIdeaInSameYouthCouncil(user.getUserId(), ideaId, youthCouncilId)) {
            IdeaLike createdIdeaLike = new IdeaLike(new IdeaLikeId(), LocalDateTime.now());
            ideaService.setIdeaOfIdeaLike(createdIdeaLike, ideaId);
            ideaService.setUserOfIdeaLike(createdIdeaLike, user.getUserId());
            ideaService.createIdeaLike(createdIdeaLike);
            return new ResponseEntity<>(
                    new IdeaLikeDto(
                            new IdeaDto(
                                    createdIdeaLike.getIdeaLikeId().getIdea().getId(),
                                    createdIdeaLike.getIdeaLikeId().getIdea().getDescription(),
                                    ideaService.getImagesOfIdea(createdIdeaLike.getIdeaLikeId().getIdea().getId()),
                                    createdIdeaLike.getIdeaLikeId().getIdea().getCreatedDate(),
                                    new UserDto(
                                            createdIdeaLike.getIdeaLikeId().getIdea().getAuthor().getId(),
                                            createdIdeaLike.getIdeaLikeId().getIdea().getAuthor().getUsername()
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
                                    createdIdeaLike.getIdeaLikeId().getLikedBy().getUsername()
                            ),
                            createdIdeaLike.getLikedDateTime()
                    ),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("/{ideaId}/comments")
    public ResponseEntity<IdeaCommentDto> addActionPointComment(@PathVariable("youthCouncilId") long youthCouncilId,
                                                                @PathVariable("ideaId") long ideaId,
                                                                @RequestBody @Valid NewIdeaCommentDto newIdeaCommentDto,
                                                                @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("ActionPointsController is running addActionPointComment");
        if (ideaService.userAndIdeaInSameYouthCouncil(user.getUserId(), ideaId, youthCouncilId)) {
            IdeaComment ideaComment = new IdeaComment();
            ideaComment.setContent(newIdeaCommentDto.getContent());
            ideaComment.setCreatedDate(LocalDateTime.now());
            ideaService.setAuthorOfIdeaComment(ideaComment, user.getUserId());
            ideaService.setIdeaOfIdeaComment(ideaComment, ideaId);
            ideaComment = ideaService.createIdeaComment(ideaComment);
            return new ResponseEntity<>(new IdeaCommentDto(
                    ideaComment.getId(),
                    new UserDto(
                            ideaComment.getAuthor().getId(),
                            ideaComment.getAuthor().getUsername()
                    ),
                    new IdeaDto(
                            ideaComment.getIdea().getId(),
                            ideaComment.getIdea().getDescription(),
                            ideaService.getImagesOfIdea(ideaComment.getIdea().getId()),
                            ideaComment.getIdea().getCreatedDate(),
                            new UserDto(
                                    ideaComment.getIdea().getAuthor().getId(),
                                    ideaComment.getIdea().getAuthor().getUsername()
                            ),
                            new ThemeDto(
                                    ideaComment.getIdea().getTheme().getId(),
                                    ideaComment.getIdea().getTheme().getName()
                            ),
                            new YouthCouncilDto(
                                    ideaComment.getIdea().getYouthCouncil().getId(),
                                    ideaComment.getIdea().getYouthCouncil().getName(),
                                    ideaComment.getIdea().getYouthCouncil().getMunicipalityName())),
                    ideaComment.getContent(),
                    ideaComment.getCreatedDate()
            ),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

}
