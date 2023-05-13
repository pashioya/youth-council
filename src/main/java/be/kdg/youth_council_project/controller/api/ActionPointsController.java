package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.ActionPointCommentDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.ActionPointDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.NewActionPointCommentDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point.NewActionPointDto;
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
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/action-points")
public class ActionPointsController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ActionPointService actionPointService;

    private final IdeaService ideaService;

    private final UserService userService;

    private final ModelMapper modelMapper;

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
                            actionPoint -> actionPointService.mapToDto(
                                    actionPoint, tenantId
                            )).toList()
                    , HttpStatus.OK);
        }
    }

    @GetMapping("/{actionPointId}")
    public ResponseEntity<ActionPointDto> getActionPoint(@TenantId long tenantId,
                                                         @PathVariable("actionPointId") long actionPointId) {
        LOGGER.info("ActionPointsController is running getActionPoint");
        ActionPoint actionPoint = actionPointService.getActionPointById(tenantId, actionPointId);
        return new ResponseEntity<>(
                actionPointService.mapToDto(actionPoint, tenantId)
                , HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ActionPointDto> addActionPoint(@TenantId long tenantId,
                                                         @RequestPart("actionPoint") @Valid NewActionPointDto newActionPointDto,
                                                         @RequestPart("images") List<MultipartFile> images,
                                                         @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("ActionPointsController is running addActionPoint");
        if (userService.userBelongsToYouthCouncil(user.getUserId(), tenantId)) {
            // Add and map
            ActionPoint actionPoint = actionPointService.addFromDto(newActionPointDto,images, tenantId);
            ActionPointDto actionPointDto = actionPointService.mapToDto(actionPoint, tenantId);
            return new ResponseEntity<>(actionPointDto, HttpStatus.CREATED);
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
                        actionPointService.mapToDto(actionPointComment.getActionPoint(), tenantId),
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



