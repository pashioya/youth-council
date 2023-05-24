package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UpdateUserDto;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.UserService;
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

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ResponseEntity<HttpStatus> deleteUser(@TenantId long tenantId, @PathVariable("userId") long userId) {
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

    @DeleteMapping("/delete_user")
//    todo: add security
    public ResponseEntity<HttpStatus> deleteUserAccount(@AuthenticationPrincipal CustomUserDetails user,
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
    public ResponseEntity<HttpStatus> updatePassword(@PathVariable("userId") long userId,
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
