package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.UserDto;
import be.kdg.youth_council_project.domain.platform.Membership;
import be.kdg.youth_council_project.domain.platform.Role;
import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.repository.MembershipRepository;
import be.kdg.youth_council_project.repository.UserRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Member;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<Membership>> getYouthCouncilAdmins(@PathVariable("id") long youthCouncilId) {
        LOGGER.info("GeneralAdminDashboardController is running getYouthCouncilAdmins");
        LOGGER.info("users of yc : ",
                membershipRepository.findMembersOfYouthCouncilByYouthCouncilId(youthCouncilId).stream().map(Membership::getRole).collect(Collectors.toList()));

        return ResponseEntity.ok(List.of(new Membership()));

    }



}
