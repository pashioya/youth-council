package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UpdateUserDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.IdeaDto;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.UserService;
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

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final IdeaService ideaService;
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private ModelMapper modelMapper;

    @GetMapping("{userId}/ideas")
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

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ResponseEntity<Void> deleteUser(@TenantId long tenantId, @PathVariable("userId") long userId) {
        LOGGER.info("UsersController is running deleteUser");
        try {
            userService.deleteUser(userId, tenantId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/self")
    public ResponseEntity<HttpStatus> deleteOwnAccount(@AuthenticationPrincipal CustomUserDetails user,
                                                       @TenantId long tenantId) {
        LOGGER.info("UsersController is running deleteOwnAccount");
        try {
            userService.deleteUser(user.getUserId(), tenantId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("{userId}")
    public ResponseEntity<Void> updatePassword(@PathVariable("userId") long userId,
                                               @AuthenticationPrincipal CustomUserDetails user,
                                               @Valid @RequestBody UpdateUserDto newPassword) {
        LOGGER.info("UsersController is running updatePassword");
        try {
//            if the user is not an admin, he can only change his own password. General admins can change any password.
            if (userId != user.getUserId()) {
                if (user.getAuthorities().stream().noneMatch(
                        authority -> authority.getAuthority().equals("ROLE_GENERAL_ADMINISTRATOR"))) {
                    return ResponseEntity.badRequest().build();
                }
            }
            userService.updatePassword(userId, newPassword.getPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
