package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.StandardActionDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import be.kdg.youth_council_project.service.youth_council_items.StandardActionService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/standard-actions")
@NoTenantController
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
public class StandardActionController {

    private final StandardActionService standardActionService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ModelMapper modelMapper;

    @GetMapping("/{standardActionId}")
    public ResponseEntity<StandardActionDto> getStandardActionById(
            @PathVariable long standardActionId
    ) {
        LOGGER.info("StandardActionController is running getStandardActionById");
        try{
            StandardAction standardAction = standardActionService.getStandardActionById(standardActionId);
            return ResponseEntity.ok(modelMapper.map(standardAction, StandardActionDto.class));
        } catch (Exception e) {
            LOGGER.error("StandardActionController is running getStandardActionById and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{standardActionId}")
    public ResponseEntity<Void> deleteStandardAction(
            @PathVariable long standardActionId
    ) {
        LOGGER.info("StandardActionController is running deleteStandardAction");
        try{
            standardActionService.deleteStandardAction(standardActionId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            LOGGER.error("StandardActionController is running deleteStandardAction and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{standardActionId}")
    public ResponseEntity<StandardActionDto> updateStandardAction(
            @PathVariable long standardActionId,
            @RequestBody String name
    ) {
        LOGGER.info("StandardActionController is running updateStandardAction");
        try{
            standardActionService.updateStandardAction(standardActionId, name);
            return new ResponseEntity<>(
                    modelMapper.map(
                            standardActionService.getStandardActionById(standardActionId),
                            StandardActionDto.class),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            LOGGER.error("StandardActionController is running updateStandardAction and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/{themId}")
    public ResponseEntity<StandardActionDto> createStandardAction(
            @PathVariable long themId,
            @RequestBody String name
    ) {
        LOGGER.info("StandardActionController is running createStandardAction");
        try{
            StandardAction createdStandardAction =  standardActionService.createStandardAction(themId, name);
            return new ResponseEntity<>(modelMapper.map(createdStandardAction, StandardActionDto.class), HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("StandardActionController is running createStandardAction and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }
}
