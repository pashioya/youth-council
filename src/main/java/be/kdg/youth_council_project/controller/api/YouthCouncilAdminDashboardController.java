package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.CommentDto;
import be.kdg.youth_council_project.controller.api.dtos.StatsCommentDto;
import be.kdg.youth_council_project.controller.api.dtos.StatsIdeaDto;
import be.kdg.youth_council_project.controller.api.dtos.StatsUserDto;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.ActionPointService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.NewsItemService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/yc-admin")
public class YouthCouncilAdminDashboardController {

    private final UserService userService;
    private final IdeaService ideaService;
    private final ActionPointService actionPointService;
    private final NewsItemService newsItemService;
    private final ModelMapper modelMapper;

    @GetMapping("/stats/users")
    public ResponseEntity<List<StatsUserDto>> getAllStatsUsers(
            @TenantId long tenantId
    ) {
        List<User> users = userService.getAllNonDeletedUsersForYouthCouncil(tenantId);
        return ResponseEntity.ok().body(
                users
                        .stream()
                        .map(user -> modelMapper.map(user, StatsUserDto.class))
                        .toList()
        );
    }

    @GetMapping("/stats/ideas")
    public ResponseEntity<List<StatsIdeaDto>> getAllStatsIdeas(
            @TenantId long tenantId
    ) {

        List<Idea> ideas = ideaService.getAllIdeasByYouthCouncilId(tenantId);
        return ResponseEntity.ok().body(
                ideas
                        .stream()
                        .map(idea -> modelMapper.map(idea, StatsIdeaDto.class))
                        .toList()
        );
    }

    @GetMapping("/stats/comments")
    public ResponseEntity<List<StatsCommentDto>> getAllStatsComments(
            @TenantId long tenantId
    ) {

        List<CommentDto> comments = new ArrayList<>();

        comments.addAll(actionPointService.getAllCommentsByYouthCouncilId(tenantId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList());

        comments.addAll(ideaService.getAllCommentsByYouthCouncilId(tenantId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList());

        comments.addAll(newsItemService.getAllCommentsByYouthCouncilId(tenantId)
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList());

        return ResponseEntity.ok().body(
                comments
                        .stream()
                        .map(idea -> modelMapper.map(idea, StatsCommentDto.class))
                        .toList()
        );
    }
}
