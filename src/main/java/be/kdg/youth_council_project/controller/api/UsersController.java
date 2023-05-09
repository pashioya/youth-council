package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UpdateUserDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.IdeaDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.tenants.TenantId;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final IdeaService ideaService;

    private final UserService userService;
    private ModelMapper modelMapper;

    public UsersController(IdeaService ideaService, UserService userService) {
        this.ideaService = ideaService;
        this.userService = userService;
    }

    @GetMapping("{userId}/ideas")
    public ResponseEntity<List<IdeaDto>> getIdeasOfUser(@TenantId long tenantId, @PathVariable("userId") long userId) {
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

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        var users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(
                    users.stream().map(
                            user -> new UserDto(
                                    user.getId(),
                                    user.getUsername()
                            )).toList()
                    , HttpStatus.OK);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@TenantId long tenantId, @PathVariable("userId") long userId, @PathVariable long ideaId) {
        if (userService.userExists(userId)) {
            userService.deleteUser(userId, tenantId, ideaId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<Void> updatePassword(long userId,
                                               @Valid @RequestBody UpdateUserDto newPassword) {
        if (userService.updatePassword(userId, newPassword.getPassword())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
