package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ElectionDto;
import be.kdg.youth_council_project.service.youth_council_items.ElectionService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ElectionController {
    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final ElectionService electionService;

    @PostMapping("/api/elections")
    public ResponseEntity<ElectionDto> createElection(@RequestBody ElectionDto electionDto, @TenantId long tenantId) {
        LOGGER.info("ElectionController is running createElection with electionDto {}", electionDto);
        return new ResponseEntity<>(electionService.mapToDto(electionService.createElection(electionDto, tenantId)),
                HttpStatus.CREATED);
    }

}
