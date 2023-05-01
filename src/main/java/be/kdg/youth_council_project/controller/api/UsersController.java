package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.OneUserDto;
import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.IdeaDto;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.tenants.TenantId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final IdeaService ideaService;

    private final UserService userService;

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

//    @GetMapping("{userId}")
//    public ResponseEntity<OneUserDto> getUser(
//            @PathVariable("userId") long userId
//    ) {
//        var user = userService.getUser(userId);
//        if (user == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(
//                    new OneUserDto(
//                            user.get().getId(),
//                            user.get().getFirstName(),
//                            user.get().getLastName(),
//                            user.get().getUsername(),
//                            user.get().getEmail(),
//                            user.get().getPostCode(),
//                            user.get().getPassword()), HttpStatus.OK);
//        }
//    }
}
