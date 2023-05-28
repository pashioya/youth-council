package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.ActivityDto;
import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.NewActivityDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Activity;
import be.kdg.youth_council_project.service.youth_council_items.ActivityService;
import be.kdg.youth_council_project.tenants.TenantId;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NewsItemController.class);
    private final ActivityService activityService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ActivityDto>> getAllActivities(@TenantId long tenantId) {
        LOGGER.info("ActivityController is running getAllActivities()");
        List<Activity> activities = activityService.getActivitiesByYouthCouncilId(tenantId);
        if (activities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<ActivityDto> activityDtos = activities.stream().map(activity1 -> modelMapper.map(activity1,
                ActivityDto.class)).toList();
        return new ResponseEntity<>(activityDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ActivityDto> addActivity(@TenantId long tenantId,
                                                   @RequestPart("activity") @Valid NewActivityDto NewActivityDto) {
        LOGGER.info("ActivityController is running addActivity()");
        Activity activity = new Activity(NewActivityDto.getName(), NewActivityDto.getDescription(), NewActivityDto.getStartDate(), NewActivityDto.getEndDate());
        activityService.setYouthCouncilOfActivity(activity, tenantId);
        Activity createdActivity = activityService.createActivity(activity);
        return new ResponseEntity<>(modelMapper.map(createdActivity, ActivityDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{activityId}")
    @PreAuthorize("hasRole('ROLE_YOUTH_COUNCIL_ADMINISTRATOR') or hasRole('ROLE_YOUTH_COUNCIL_MODERATOR')")
    public ResponseEntity<HttpStatus> deleteActivity(@PathVariable("activityId") long id, @TenantId long tenantId) {
        LOGGER.info("ActivityController is running deleteActivity");
        try {
            activityService.deleteActivity(id, tenantId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("ActivityController is running deleteActivity and has thrown an exception: " + e);
            return ResponseEntity.badRequest().build();
        }
    }
}
