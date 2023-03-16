package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.service.ActionPointService;
import be.kdg.youth_council_project.service.IdeaService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/youth-councils/{youthCouncilId}/action-points")
public class ActionPointsController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final ActionPointService actionPointService;

    private final IdeaService ideaService;


    @GetMapping()
    public ResponseEntity<List<ActionPointDto>> getAllActionPoints(@PathVariable("youthCouncilId") long youthCouncilId) {
        LOGGER.info("ActionPointsController is running getAllActionPoints");
        var actionPoints = actionPointService.getActionPointsByYouthCouncilId(youthCouncilId);
        if (actionPoints.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NO_CONTENT);
        } else {
            LOGGER.info("ActionPointsController found action points {}", actionPoints);
            return new ResponseEntity<>(
                    actionPoints.stream().map(
                            actionPoint -> new ActionPointDto(
                                    actionPoint.getId(),
                                    actionPoint.getTitle(),
                                    actionPoint.getDescription(),
                                    actionPoint.getVideo(),
                                    actionPoint.getStatus(),
                                    actionPointService.getImagesOfActionPoint(actionPoint.getId()),
                                    actionPoint.getCreatedDate(),
                                    actionPointService.getIdeasOfActionPoint(actionPoint.getId()).stream().map(
                                            idea -> new IdeaDto(
                                                    idea.getId(),
                                                    idea.getDescription(),
                                                    ideaService.getImagesOfIdea(idea.getId()),
                                                    idea.getCreatedDate(),
                                                    new UserDto(
                                                            idea.getAuthor().getId(),
                                                            idea.getAuthor().getFirstName()
                                                    ),
                                                    new ThemeDto(
                                                            idea.getTheme().getId(),
                                                            idea.getTheme().getName()
                                                    ),
                                                    new YouthCouncilDto(
                                                            idea.getYouthCouncil().getId(),
                                                            idea.getYouthCouncil().getName(),
                                                            idea.getYouthCouncil().getMunicipalityName()
                                                    )
                                            )).toList(),
                                    new StandardActionDto(
                                            actionPoint.getLinkedStandardAction().getName(),
                                            new ThemeDto(
                                                    actionPoint.getLinkedStandardAction().getTheme().getId(),
                                                    actionPoint.getLinkedStandardAction().getTheme().getName()
                                            )
                                    ),
                                    new YouthCouncilDto(
                                            actionPoint.getYouthCouncil().getId(),
                                            actionPoint.getYouthCouncil().getName(),
                                            actionPoint.getYouthCouncil().getMunicipalityName()
                                    )
                            )).toList()
                    , HttpStatus.OK);
        }
    }

    @GetMapping("/{actionPointId}")
    public ResponseEntity<ActionPointDto> getActionPoint(@PathVariable("youthCouncilId") long youthCouncilId,
                                                         @PathVariable("actionPointId") long actionPointId) {
        LOGGER.info("ActionPointsController is running getActionPoint");
        var actionPoint = actionPointService.getActionPointById(youthCouncilId, actionPointId);
        LOGGER.info("ActionPointsController found action point {}", actionPoint);
        return new ResponseEntity<>(new ActionPointDto(
                actionPoint.getId(),
                actionPoint.getTitle(),
                actionPoint.getDescription(),
                actionPoint.getVideo(),
                actionPoint.getStatus(),
                actionPointService.getImagesOfActionPoint(actionPoint.getId()),
                actionPoint.getCreatedDate(),
                actionPointService.getIdeasOfActionPoint(actionPoint.getId()).stream().map(
                        idea -> new IdeaDto(
                                idea.getId(),
                                idea.getDescription(),
                                ideaService.getImagesOfIdea(idea.getId()),
                                idea.getCreatedDate(),
                                new UserDto(
                                        idea.getAuthor().getId(),
                                        idea.getAuthor().getFirstName()
                                ),
                                new ThemeDto(
                                        idea.getTheme().getId(),
                                        idea.getTheme().getName()
                                ),
                                new YouthCouncilDto(
                                        idea.getYouthCouncil().getId(),
                                        idea.getYouthCouncil().getName(),
                                        idea.getYouthCouncil().getMunicipalityName()
                                )
                        )).toList(),
                new StandardActionDto(
                        actionPoint.getLinkedStandardAction().getName(),
                        new ThemeDto(
                                actionPoint.getLinkedStandardAction().getTheme().getId(),
                                actionPoint.getLinkedStandardAction().getTheme().getName()
                        )
                ),
                new YouthCouncilDto(
                        actionPoint.getYouthCouncil().getId(),
                        actionPoint.getYouthCouncil().getName(),
                        actionPoint.getYouthCouncil().getMunicipalityName()
                )
        )
                , HttpStatus.OK);
    }
}



