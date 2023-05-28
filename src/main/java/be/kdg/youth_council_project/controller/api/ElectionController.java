package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.ElectionDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;
import be.kdg.youth_council_project.service.youth_council_items.ElectionService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ElectionController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final ElectionService electionService;

    @GetMapping("/api/elections")
    public ResponseEntity<List<ElectionDto>> getElections(@TenantId long tenantId) {
        LOGGER.info("ElectionController is running getElections");
        List<Election> elections = electionService.getAllElectionsByYouthCouncilId(tenantId);
        if (elections.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ElectionDto> electionDtos =
                elections.parallelStream()
                        .map(election -> electionService.mapToDto(election))
                        .toList();
        return new ResponseEntity<>(electionDtos,
                HttpStatus.OK);
    }

    @PostMapping("/api/elections")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<ElectionDto> createElection(@RequestBody ElectionDto electionDto, @TenantId long tenantId) {
        LOGGER.info("ElectionController is running createElection with electionDto {}", electionDto);
        return new ResponseEntity<>(electionService.mapToDto(electionService.createElection(electionDto, tenantId)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/api/elections/{id}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<ElectionDto> deleteElection(@PathVariable long id, @TenantId long tenantId) {
        LOGGER.info("ElectionController is running deleteElection with id {}", id);
        electionService.deleteElection(id, tenantId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/api/elections/{id}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR')")
    public ResponseEntity<ElectionDto> updateElection(@PathVariable long id, @RequestBody ElectionDto electionDto, @TenantId long tenantId) {
        LOGGER.info("ElectionController is running updateElection with id {}", id);
        return new ResponseEntity<>(electionService.mapToDto(electionService.updateElection(electionDto, tenantId)),
                HttpStatus.NO_CONTENT);
    }

}
