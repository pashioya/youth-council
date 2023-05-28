package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
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
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<IdeaDto> addIdea(@TenantId long tenantId,
                                           @RequestPart("idea") @Valid NewIdeaDto newIdeaDto,
                                           @RequestPart("images") List<MultipartFile> images,
                                           @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("IdeasController is running submitIdea");
        Idea idea = new Idea(newIdeaDto.getDescription());
        ideaService.setAuthorOfIdea(idea, user.getUserId(), tenantId);
        ideaService.setThemeOfIdea(idea, newIdeaDto.getThemeId());
        ideaService.setYouthCouncilOfIdea(idea, tenantId);
        Idea createdIdea = ideaService.createIdea(idea);
        if (!FileUtils.checkImageFileList(images)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        images.forEach(image -> ideaService.addImageToIdea(createdIdea, image));
        return new ResponseEntity<>(
                new IdeaDto(
                        createdIdea.getId(),
                        createdIdea.getDescription(),
                        ideaService.getImagesOfIdea(createdIdea.getId()).stream().map(
                                image -> Base64.getEncoder().encodeToString(image.getImage()
                                )).collect(Collectors.toList()),
                        createdIdea.getCreatedDate(),
                        modelMapper.map(createdIdea.getAuthor(), UserDto.class),
                        modelMapper.map(createdIdea.getTheme(), ThemeDto.class),
                        modelMapper.map(createdIdea.getYouthCouncil(), YouthCouncilDto.class)
                ),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{ideaId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR') or hasRole('ROLE_YOUTH_COUNCIL_MODERATOR')")
    public ResponseEntity<HttpStatus> deleteIdea(@TenantId long tenantId,
                                                 @PathVariable("ideaId") long ideaId) {
        LOGGER.info("IdeasController is running deleteIdea");
        try {
            ideaService.deleteIdea(ideaId, tenantId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            LOGGER.error("IdeasController is running deleteIdea and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
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
                        modelMapper.map(idea.getAuthor(), UserDto.class),
                        modelMapper.map(idea.getTheme(), ThemeDto.class),
                        modelMapper.map(idea.getYouthCouncil(), YouthCouncilDto.class)),
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
                                    modelMapper.map(idea.getAuthor(), UserDto.class),
                                    modelMapper.map(idea.getTheme(), ThemeDto.class),
                                    modelMapper.map(idea.getYouthCouncil(), YouthCouncilDto.class)
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
                                    modelMapper.map(createdIdeaLike.getId().getIdea().getAuthor(), UserDto.class),
                                    modelMapper.map(createdIdeaLike.getId().getIdea().getTheme(), ThemeDto.class),
                                    modelMapper.map(createdIdeaLike.getId().getIdea().getYouthCouncil(), YouthCouncilDto.class)

                            ),
                            modelMapper.map(createdIdeaLike.getId().getLikedBy(), UserDto.class),
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
        IdeaComment createdIdeaComment = ideaService.createIdeaComment(ideaComment);
        return new ResponseEntity<>(new IdeaCommentDto(
                createdIdeaComment.getId(),
                new UserDto(
                        createdIdeaComment.getAuthor().getId(),
                        createdIdeaComment.getAuthor().getUsername()
                ),
                new IdeaDto(
                        createdIdeaComment.getIdea().getId(),
                        createdIdeaComment.getIdea().getDescription(),
                        ideaService.getImagesOfIdea(createdIdeaComment.getIdea().getId()).stream().map(
                                image -> Base64.getEncoder().encodeToString(image.getImage()
                                )).collect(Collectors.toList()),
                        createdIdeaComment.getIdea().getCreatedDate(),
                        modelMapper.map(createdIdeaComment.getIdea().getAuthor(), UserDto.class),
                        modelMapper.map(createdIdeaComment.getIdea().getTheme(), ThemeDto.class),
                        modelMapper.map(createdIdeaComment.getIdea().getYouthCouncil(), YouthCouncilDto.class)
                ),
                createdIdeaComment.getContent(),
                createdIdeaComment.getCreatedDate()
        ), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IdeaDto>> getIdeasOfUser(@TenantId long tenantId, @PathVariable("userId") long userId) {
        LOGGER.info("UsersController is running getIdeasOfUser");
        var ideas = ideaService.getIdeasByYouthCouncilIdAndUserId(tenantId, userId);
        if (ideas.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
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
                                    modelMapper.map(idea.getAuthor(), UserDto.class),
                                    modelMapper.map(idea.getTheme(), ThemeDto.class),
                                    modelMapper.map(idea.getYouthCouncil(), YouthCouncilDto.class)
                            )).toList()
                    , HttpStatus.OK);
        }
    }

    @DeleteMapping("/{ideaId}/{commentId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR') or hasRole('ROLE_YOUTH_COUNCIL_MODERATOR')")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("ideaId") long ideaId, @PathVariable("commentId") long commentId) {
        LOGGER.info("IdeasController is running deleteComment");
        try {
            ideaService.deleteIdeasComment(ideaId, commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            LOGGER.error("IdeasController is running deleteComment and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }
}



