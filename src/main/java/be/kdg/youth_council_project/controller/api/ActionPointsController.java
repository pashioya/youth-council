package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.StandardActionDto;
import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.*;
import be.kdg.youth_council_project.domain.platform.youth_council_items.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLikeId;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.ActionPointService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/action-points")
public class ActionPointsController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ActionPointService actionPointService;
    private final IdeaService ideaService;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<ActionPointDto>> getActionPointsOfYouthCouncil(@TenantId long tenantId) {
        LOGGER.info("ActionPointsController is running getAllActionPoints");
        var actionPoints = actionPointService.getActionPointsByYouthCouncilId(tenantId);
        if (actionPoints.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("ActionPointsController found action points {}", actionPoints);
            return new ResponseEntity<>(
                    actionPoints.stream().map(
                            actionPoint -> new ActionPointDto(
                                    actionPoint.getId(),
                                    actionPoint.getTitle(),
                                    actionPoint.getDescription(),
                                    actionPoint.getVideo(),
                                    actionPoint.getStatus(),
                                    actionPointService.getImagesOfActionPoint(actionPoint.getId()),
                                    actionPoint.getCreatedDate(),
                                    actionPointService.getIdeasOfActionPoint(actionPoint.getId(), tenantId).stream().map(
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
                                                            idea.getYouthCouncil().getMunicipalityName()
                                                    )
                                            )).toList(),
                                    new StandardActionDto(
                                            actionPoint.getLinkedStandardAction().getName(),
                                            new ThemeDto(
                                                    actionPoint.getLinkedStandardAction().getTheme().getId(),
                                                    actionPoint.getLinkedStandardAction().getTheme().getName()
                                            )
                                    ),
                                    new YouthCouncilDto(
                                            actionPoint.getYouthCouncil().getId(),
                                            actionPoint.getYouthCouncil().getName(),
                                            actionPoint.getYouthCouncil().getMunicipalityName()
                                    )
                            )).toList()
                    , HttpStatus.OK);
        }
    }

    @GetMapping("/{actionPointId}")
    public ResponseEntity<ActionPointDto> getActionPoint(@TenantId long tenantId,
                                                         @PathVariable("actionPointId") long actionPointId) {
        LOGGER.info("ActionPointsController is running getActionPoint");
        var actionPoint = actionPointService.getActionPointById(tenantId, actionPointId);
        LOGGER.info("ActionPointsController found action point {}", actionPoint);
        return new ResponseEntity<>(new ActionPointDto(
                actionPoint.getId(),
                actionPoint.getTitle(),
                actionPoint.getDescription(),
                actionPoint.getVideo(),
                actionPoint.getStatus(),
                actionPointService.getImagesOfActionPoint(actionPoint.getId()),
                actionPoint.getCreatedDate(),
                actionPointService.getIdeasOfActionPoint(actionPoint.getId(), tenantId).stream().map(
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
                                        idea.getYouthCouncil().getMunicipalityName()
                                )
                        )).toList(),
                new StandardActionDto(
                        actionPoint.getLinkedStandardAction().getName(),
                        new ThemeDto(
                                actionPoint.getLinkedStandardAction().getTheme().getId(),
                                actionPoint.getLinkedStandardAction().getTheme().getName()
                        )
                ),
                new YouthCouncilDto(
                        actionPoint.getYouthCouncil().getId(),
                        actionPoint.getYouthCouncil().getName(),
                        actionPoint.getYouthCouncil().getMunicipalityName()
                )
        )
                , HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<ActionPointDto> addActionPoint(@TenantId long tenantId,
                                                         @RequestBody @Valid NewActionPointDto newActionPointDto,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("ActionPointsController is running addActionPoint");
        if (userService.userBelongsToYouthCouncil(user.getUserId(), tenantId)) {
            ActionPoint actionPoint = new ActionPoint(
                    newActionPointDto.getTitle(),
                    newActionPointDto.getVideo(),
                    newActionPointDto.getDescription(),
                    newActionPointDto.getImages(),
                    LocalDateTime.now()
            );
            actionPointService.setYouthCouncilOfActionPoint(actionPoint, tenantId);
            actionPointService.setStatusOfActionPoint(actionPoint, newActionPointDto.getStatusName());
            actionPointService.setLinkedIdeasOfActionPoint(actionPoint, newActionPointDto.getLinkedIdeaIds(), tenantId);
            actionPointService.setStandardActionOfActionPoint(actionPoint, newActionPointDto.getStandardActionId());
            actionPointService.createActionPoint(actionPoint);
            return new ResponseEntity<>(
                    new ActionPointDto(
                            actionPoint.getId(),
                            actionPoint.getTitle(),
                            actionPoint.getDescription(),
                            actionPoint.getVideo(),
                            actionPoint.getStatus(),
                            actionPointService.getImagesOfActionPoint(actionPoint.getId()),
                            actionPoint.getCreatedDate(),
                            actionPointService.getIdeasOfActionPoint(actionPoint.getId(), tenantId).stream().map(
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
                                                    idea.getYouthCouncil().getMunicipalityName()
                                            )
                                    )).toList(),
                            new StandardActionDto(
                                    actionPoint.getLinkedStandardAction().getName(),
                                    new ThemeDto(
                                            actionPoint.getLinkedStandardAction().getTheme().getId(),
                                            actionPoint.getLinkedStandardAction().getTheme().getName()
                                    )
                            ),
                            new YouthCouncilDto(
                                    actionPoint.getYouthCouncil().getId(),
                                    actionPoint.getYouthCouncil().getName(),
                                    actionPoint.getYouthCouncil().getMunicipalityName()
                            )
                    )
                    , HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{actionPointId}/comments")
    public ResponseEntity<ActionPointCommentDto> addActionPointComment(@TenantId long tenantId,
                                                                       @PathVariable("actionPointId") long actionPointId,
                                                                       @RequestBody @Valid NewActionPointCommentDto newActionPointCommentDto,
                                                                       @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("ActionPointsController is running addActionPointComment");
        ActionPointComment actionPointComment = new ActionPointComment();
        actionPointComment.setContent(newActionPointCommentDto.getContent());
        actionPointComment.setCreatedDate(LocalDateTime.now());
        actionPointService.setAuthorOfActionPointComment(actionPointComment, user.getUserId());
        actionPointService.setActionPointOfActionPointComment(actionPointComment, actionPointId, tenantId);
        actionPointService.createActionPointComment(actionPointComment);
        return new ResponseEntity<>(
                new ActionPointCommentDto(
                        actionPointComment.getId(),
                        new UserDto(
                                actionPointComment.getAuthor().getId(),
                                actionPointComment.getAuthor().getUsername()
                        ),
                        new ActionPointDto(
                                actionPointComment.getActionPoint().getId(),
                                actionPointComment.getActionPoint().getTitle(),
                                actionPointComment.getActionPoint().getDescription(),
                                actionPointComment.getActionPoint().getVideo(),
                                actionPointComment.getActionPoint().getStatus(),
                                actionPointService.getImagesOfActionPoint(actionPointComment.getActionPoint().getId()),
                                actionPointComment.getActionPoint().getCreatedDate(),
                                actionPointService.getIdeasOfActionPoint(actionPointComment.getActionPoint().getId(), tenantId).stream().map(
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
                                                        idea.getYouthCouncil().getMunicipalityName()
                                                )
                                        )).toList(),
                                new StandardActionDto(
                                        actionPointComment.getActionPoint().getLinkedStandardAction().getName(),
                                        new ThemeDto(
                                                actionPointComment.getActionPoint().getLinkedStandardAction().getTheme().getId(),
                                                actionPointComment.getActionPoint().getLinkedStandardAction().getTheme().getName()
                                        )
                                ),
                                new YouthCouncilDto(
                                        actionPointComment.getActionPoint().getYouthCouncil().getId(),
                                        actionPointComment.getActionPoint().getYouthCouncil().getName(),
                                        actionPointComment.getActionPoint().getYouthCouncil().getMunicipalityName()
                                )
                        ),
                        actionPointComment.getContent(),
                        actionPointComment.getCreatedDate()
                )
                , HttpStatus.CREATED);
    }


    @PostMapping("/{actionPointId}/likes")
    public ResponseEntity<HttpStatus> addActionPointLike(@TenantId long tenantId,
                                                         @PathVariable("actionPointId") long actionPointId,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("ActionPointsController is running likeActionPoint");
        ActionPointLike createdActionPointLike = new ActionPointLike(new ActionPointLikeId(), LocalDateTime.now());
        actionPointService.setActionPointOfActionPointLike(createdActionPointLike, actionPointId, tenantId);
        actionPointService.setUserOfActionPointLike(createdActionPointLike, user.getUserId());
        actionPointService.createActionPointLike(createdActionPointLike);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping("/{actionPointId}/likes")
    public ResponseEntity<Integer> removeActionPointLike(@TenantId long tenantId,
                                                         @PathVariable("actionPointId") long actionPointId,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        actionPointService.removeActionPointLike(actionPointId, user.getUserId(), tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



