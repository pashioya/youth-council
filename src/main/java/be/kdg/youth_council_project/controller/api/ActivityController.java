package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.ActivityDto;
import be.kdg.youth_council_project.controller.api.dtos.NewActivityDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;

import be.kdg.youth_council_project.service.ActivityService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NewsItemController.class);
    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ActivityDto> addActivity(@TenantId long tenantId,
                                                   @RequestPart("activity") @Valid NewActivityDto NewActivityDto) {
        LOGGER.info("ActivityController is running addActivity()");
        Activity createdActivity = new Activity(NewActivityDto.getName(), NewActivityDto.getDescription(), NewActivityDto.getStartDate(), NewActivityDto.getEndDate());
        activityService.setYouthCouncilOfActivity(createdActivity, tenantId);
        activityService.createActivity(createdActivity);
        return new ResponseEntity<>(modelMapper.map(createdActivity, ActivityDto.class), HttpStatus.CREATED);
    }

}
