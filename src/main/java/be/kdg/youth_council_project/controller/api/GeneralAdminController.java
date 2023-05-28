package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.StatsCommentDto;
import be.kdg.youth_council_project.controller.api.dtos.StatsIdeaDto;
import be.kdg.youth_council_project.controller.api.dtos.StatsUserDto;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.ActionPointService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.service.youth_council_items.NewsItemService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/ga")
@NoTenantController
@PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
public class GeneralAdminController {

    private final UserService userService;
    private final IdeaService ideaService;
    private final ActionPointService actionPointService;
    private final NewsItemService newsItemService;
    private final ModelMapper modelMapper;


    @GetMapping("/stats/users")
    public ResponseEntity<List<StatsUserDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users.stream()
                .map(user -> modelMapper.map(user, StatsUserDto.class))
                .toList(), HttpStatus.OK);
    }


    @GetMapping("/stats/ideas")
    public ResponseEntity<List<StatsIdeaDto>> getAllIdeas() {
        List<Idea> ideas = ideaService.getAllIdeas();
        if (ideas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ideas.stream()
                .map(idea -> modelMapper.map(idea, StatsIdeaDto.class))
                .toList(), HttpStatus.OK);
    }

    @GetMapping("/stats/comments")
    public ResponseEntity<List<StatsCommentDto>> getAllComments() {
        List<StatsCommentDto> comments = new ArrayList<>();

        comments.addAll(actionPointService.getAllComments()
                .stream()
                .map(comment -> modelMapper.map(comment, StatsCommentDto.class))
                .toList());

        comments.addAll(ideaService.getAllComments()
                .stream()
                .map(comment -> modelMapper.map(comment, StatsCommentDto.class))
                .toList());

        comments.addAll(newsItemService.getAllComments()
                .stream()
                .map(comment -> modelMapper.map(comment, StatsCommentDto.class))
                .toList());

        if (comments.isEmpty()) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.ok().body(comments);
        }
    }
}
