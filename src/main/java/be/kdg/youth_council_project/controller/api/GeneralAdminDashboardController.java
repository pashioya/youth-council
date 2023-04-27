package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
public class GeneralAdminDashboardController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final MembershipRepository membershipRepository;

    private final ModelMapper modelMapper;
    @GetMapping("youth-councils/{id}/admins")
    public ResponseEntity<List<UserDto>> getYouthCouncilAdmins(
                                                                  @PathVariable("id") long youthCouncilId) {
        LOGGER.info("GeneralAdminDashboardController is running getYouthCouncilAdmins");
        List<Membership> admins = membershipRepository.findAdminsOfYouthCouncilByYouthCouncilId(youthCouncilId);
        List<UserDto> adminDtos = admins.stream().map(admin -> modelMapper.map(admin.getMembershipId().getUser(),
                UserDto.class)).toList();
        return ResponseEntity.ok(adminDtos);
    }



}
