package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ThemeDto;
import be.kdg.youth_council_project.controller.api.dtos.UpdateUserDto;
import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.IdeaDto;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.service.youth_council_items.IdeaService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final IdeaService ideaService;
    private final UserService userService;
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
    public ResponseEntity<HttpStatus> deleteUser(@TenantId long tenantId, @PathVariable("userId") long userId) {
        LOGGER.info("UsersController is running deleteUser");
        try {
            userService.deleteUser(userId, tenantId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("{userId}")
    public ResponseEntity<Void> updatePassword(@PathVariable("userId") long userId,
                                               @Valid @RequestBody UpdateUserDto newPassword) {
        LOGGER.info("UsersController is running updatePassword");
        try {
            userService.updatePassword(userId, newPassword.getPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("admins/{adminId}")
    public ResponseEntity<HttpStatus> deleteAdmin(@PathVariable("adminId") long adminId){
        LOGGER.info("GeneralAdminDashboardController is running deleteAdmin");
        try {
            userService.removeAdmin(adminId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
