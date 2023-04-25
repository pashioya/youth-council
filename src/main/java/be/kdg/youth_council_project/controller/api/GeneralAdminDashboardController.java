package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GeneralAdminDashboardController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    @GetMapping("youth-councils/{id}/admins")
    public ResponseEntity<List<UserDto>> getYouthCouncilAdmins(@PathVariable("id") long youthCouncilId) {
        LOGGER.info("GeneralAdminDashboardController is running getYouthCouncilAdmins");
        List<User> admins = userRepository.findUsersByRoleAndYouthCouncilId(Role.YOUTH_COUNCIL_ADMINISTRATOR, youthCouncilId);
        List<UserDto> adminsDto = admins.stream().map(user -> modelMapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(adminsDto);
    }


}
