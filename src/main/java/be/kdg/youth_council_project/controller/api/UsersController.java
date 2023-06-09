package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UpdateUserDto;
import be.kdg.youth_council_project.domain.platform.Role;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UsersController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_GENERAL_ADMINISTRATOR','ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") long userId,
                                                 @TenantId Long tenantId) {
        LOGGER.info("UsersController is running deleteUser");
        if (tenantId == null) {
            userService.findMembershipsByUserId(userId).forEach(membership -> userService.deleteUser(membership.getMembershipId().getUser().getId(), membership.getMembershipId().getYouthCouncil().getId()));
        } else {
            userService.deleteUser(userId, tenantId);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/self")
    public ResponseEntity<HttpStatus> deleteOwnAccount(@AuthenticationPrincipal CustomUserDetails user,
                                                       HttpServletRequest request)
            throws ServletException {
        LOGGER.info("UsersController is running deleteOwnAccount");
        request.logout();
        userService.findMembershipsByUserId(user.getUserId()).forEach(membership -> userService.deleteUser(membership.getMembershipId().getUser().getId(), membership.getMembershipId().getYouthCouncil().getId()));
        return ResponseEntity.noContent().build();
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

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasAnyRole('ROLE_GENERAL_ADMINISTRATOR','ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<HttpStatus> updateUserRole(@PathVariable("userId") long userId,
                                                     @Valid @RequestBody String role,
                                                     @TenantId Long tenantId) {
        LOGGER.info("UsersController is running updateUser");
        if (tenantId == null) {
            userService.findMembershipsByUserId(userId).forEach(membership -> userService.updateUserRole(membership.getMembershipId().getUser().getId(), Role.valueOf(role), membership.getMembershipId().getYouthCouncil().getId()));
        } else {
            LOGGER.info("Tenant found tenantId: " + tenantId);
            userService.updateUserRole(userId, Role.valueOf(role), tenantId);
        }
        return ResponseEntity.ok().build();
    }

}
