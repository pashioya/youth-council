package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLikeId;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.IdeaService;

import javax.validation.Valid;

import be.kdg.youth_council_project.tenants.TenantId;
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
@RequestMapping("/api/ideas")
public class IdeasController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final IdeaService ideaService;


    @PostMapping
    public ResponseEntity<IdeaDto> addIdea(@TenantId long tenantId,
                                           @RequestBody @Valid NewIdeaDto newIdeaDto,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running submitIdea");
        Idea createdIdea = new Idea(newIdeaDto.getDescription(), newIdeaDto.getImages());
        ideaService.setAuthorOfIdea(createdIdea, user.getUserId(), tenantId);
        ideaService.setThemeOfIdea(createdIdea, newIdeaDto.getThemeId());
        ideaService.setYouthCouncilOfIdea(createdIdea, tenantId);
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
    }


    @GetMapping("/{ideaId}")
    public ResponseEntity<IdeaDto> getIdea(@TenantId long tenantId,
                                           @PathVariable("ideaId") long ideaId) {
        LOGGER.info("IdeasController is running getIdea");
        var idea = ideaService.getIdeaById(tenantId, ideaId);
        LOGGER.info("IdeasController found idea {}", idea);
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
    public ResponseEntity<List<IdeaDto>> getIdeasOfYouthCouncil(@TenantId long tenantId) {
        LOGGER.info("IdeasController is running getIdeasOfYouthCouncil");
        var ideas = ideaService.getIdeasByYouthCouncilId(tenantId);
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
    public ResponseEntity<IdeaLikeDto> likeIdea(@TenantId long tenantId,
                                                @PathVariable("ideaId") long ideaId,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running likeIdea");
        IdeaLike createdIdeaLike = new IdeaLike(new IdeaLikeId(), LocalDateTime.now());
        ideaService.setIdeaOfIdeaLike(createdIdeaLike, ideaId, tenantId);
        ideaService.setUserOfIdeaLike(createdIdeaLike, user.getUserId(), tenantId);
        ideaService.createIdeaLike(createdIdeaLike);
        return new ResponseEntity<>(
                new IdeaLikeDto(
                        new IdeaDto(
                                createdIdeaLike.getId().getIdea().getId(),
                                createdIdeaLike.getId().getIdea().getDescription(),
                                ideaService.getImagesOfIdea(createdIdeaLike.getId().getIdea().getId()),
                                createdIdeaLike.getId().getIdea().getCreatedDate(),
                                new UserDto(
                                        createdIdeaLike.getId().getIdea().getAuthor().getId(),
                                        createdIdeaLike.getId().getIdea().getAuthor().getUsername()
                                ),
                                new ThemeDto(
                                        createdIdeaLike.getId().getIdea().getTheme().getId(),
                                        createdIdeaLike.getId().getIdea().getTheme().getName()
                                ),
                                new YouthCouncilDto(
                                        createdIdeaLike.getId().getIdea().getYouthCouncil().getId(),
                                        createdIdeaLike.getId().getIdea().getYouthCouncil().getName(),
                                        createdIdeaLike.getId().getIdea().getYouthCouncil().getMunicipalityName())),
                        new UserDto(
                                createdIdeaLike.getId().getLikedBy().getId(),
                                createdIdeaLike.getId().getLikedBy().getUsername()
                        ),
                        createdIdeaLike.getLikedDateTime()
                ),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{actionPointId}/likes")
    public ResponseEntity<Integer> removeIdLike(@TenantId long tenantId,
                                                @PathVariable("actionPointId") long actionPointId,
                                                @AuthenticationPrincipal CustomUserDetails user) {
        ideaService.removeIdeaLike(actionPointId, user.getUserId(), tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{ideaId}/comments")
    public ResponseEntity<IdeaCommentDto> addIdeaComment(@TenantId long tenantId,
                                                         @PathVariable("ideaId") long ideaId,
                                                         @RequestBody @Valid NewIdeaCommentDto newIdeaCommentDto,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running addIdeaComment");
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
    }

}

