package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.service.UserService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
@NoTenantController
@PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
public class GeneralAdminDashboardController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("youth-councils/{id}/admins")
    public ResponseEntity<List<UserDto>> getYouthCouncilAdmins(
            @PathVariable("id") long youthCouncilId) {
        LOGGER.info("GeneralAdminDashboardController is running getYouthCouncilAdmins");
        List<Membership> admins = userService.findAdminsOfYouthCouncilByYouthCouncilId(youthCouncilId);
        if (admins.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<UserDto> adminDtos = admins.stream().map(admin -> modelMapper.map(admin.getMembershipId().getUser(),
                UserDto.class)).toList();
        return ResponseEntity.ok(adminDtos);
    }

    @PostMapping("youth-councils/{id}/admins")
    public ResponseEntity<HttpStatus> addYouthCouncilAdmin(
            @PathVariable("id") long youthCouncilId, @RequestBody String email) {
        LOGGER.info("GeneralAdminDashboardController is running addYouthCouncilAdmin");
        userService.addAdminToYouthCouncil(youthCouncilId, email);
        LOGGER.info("user with email: " + email + " is added as admin to youth council with id: " + youthCouncilId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
