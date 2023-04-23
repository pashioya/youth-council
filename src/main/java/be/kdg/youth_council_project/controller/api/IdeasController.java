package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLikeId;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.tenants.TenantId;
import be.kdg.youth_council_project.util.FileUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@AllArgsConstructor
@RestController
@RequestMapping("/api/ideas")
public class IdeasController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    private final IdeaService ideaService;


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<IdeaDto> addIdea(@TenantId long tenantId,
                                           @RequestPart("idea") @Valid NewIdeaDto newIdeaDto,
                                           @RequestPart("images") List<MultipartFile> images,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running submitIdea");
        Idea createdIdea = new Idea(newIdeaDto.getDescription());
        ideaService.setAuthorOfIdea(createdIdea, user.getUserId(), tenantId);
        ideaService.setThemeOfIdea(createdIdea, newIdeaDto.getThemeId());
        ideaService.setYouthCouncilOfIdea(createdIdea, tenantId);
        ideaService.createIdea(createdIdea);
        if (!FileUtils.checkImageFileList(images)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        images.forEach(image -> {
            ideaService.addImageToIdea(createdIdea, image);
        });
        return new ResponseEntity<>(
                new IdeaDto(
                        createdIdea.getId(),
                        createdIdea.getDescription(),
                        ideaService.getImagesOfIdea(createdIdea.getId()).stream().map(
                                image -> Base64.getEncoder().encodeToString(image.getImage()
                                )).collect(Collectors.toList()),
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

    @DeleteMapping("/{ideaId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR') or hasRole('ROLE_YOUTH_COUNCIL_MODERATOR')")
    public ResponseEntity<Void> deleteIdea(@TenantId long tenantId,
                                           @PathVariable("ideaId") long ideaId) {
        ideaService.removeIdea(ideaId, tenantId);
        return new ResponseEntity<>(NO_CONTENT);
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
                        ideaService.getImagesOfIdea(idea.getId()).stream().map(
                                image -> Base64.getEncoder().encodeToString(image.getImage()
                                )).collect(Collectors.toList()),
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
                    NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    ideas.stream().map(
                            idea -> new IdeaDto(
                                    idea.getId(),
                                    idea.getDescription(),
                                    ideaService.getImagesOfIdea(idea.getId()).stream().map(
                                            image -> Base64.getEncoder().encodeToString(image.getImage()
                                            )).collect(Collectors.toList()),
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
        if (ideaService.createIdeaLike(createdIdeaLike)) {
            return new ResponseEntity<>(
                    new IdeaLikeDto(
                            new IdeaDto(
                                    createdIdeaLike.getId().getIdea().getId(),
                                    createdIdeaLike.getId().getIdea().getDescription(),
                                    ideaService.getImagesOfIdea(createdIdeaLike.getId().getIdea().getId()).stream().map(
                                            image -> Base64.getEncoder().encodeToString(image.getImage()
                                            )).collect(Collectors.toList()),
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
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @DeleteMapping("/{ideaId}/likes")
    public ResponseEntity<Integer> removeIdeaLike(@TenantId long tenantId,
                                                  @PathVariable("ideaId") long ideaId,
                                                  @AuthenticationPrincipal CustomUserDetails user) {
        ideaService.removeIdeaLike(ideaId, user.getUserId(), tenantId);
        return new ResponseEntity<>(NO_CONTENT);
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
        ideaService.setAuthorOfIdeaComment(ideaComment, user.getUserId(), tenantId);
        ideaService.setIdeaOfIdeaComment(ideaComment, ideaId, tenantId);
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
                        ideaService.getImagesOfIdea(ideaComment.getIdea().getId()).stream().map(
                                image -> Base64.getEncoder().encodeToString(image.getImage()
                                )).collect(Collectors.toList()),
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

