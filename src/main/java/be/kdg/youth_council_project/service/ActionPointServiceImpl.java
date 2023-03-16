package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.ActionPoint;
import be.kdg.youth_council_project.domain.platform.youthCouncilItems.Idea;
import be.kdg.youth_council_project.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ActionPointServiceImpl implements ActionPointService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final ActionPointRepository actionPointRepository;

    private final ThemeRepository themeRepository;

    private final YouthCouncilRepository youthCouncilRepository;

    private final IdeaRepository ideaRepository;

    private final StandardActionRepository standardActionRepository;

    public ActionPointServiceImpl(ActionPointRepository actionPointRepository, ThemeRepository themeRepository, YouthCouncilRepository youthCouncilRepository, IdeaRepository ideaRepository, StandardActionRepository standardActionRepository) {
        this.actionPointRepository = actionPointRepository;
        this.themeRepository = themeRepository;
        this.youthCouncilRepository = youthCouncilRepository;
        this.ideaRepository = ideaRepository;
        this.standardActionRepository = standardActionRepository;
    }

    @Override
    public List<ActionPoint> getActionPointsByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointsOfYouthCouncil");
        YouthCouncil youthCouncil = youthCouncilRepository.findById(youthCouncilId).orElseThrow(EntityNotFoundException::new);
        return actionPointRepository.findByYouthCouncil(youthCouncil);
    }

    @Override
    @Transactional
    public List<Idea> getIdeasOfActionPoint(long actionPointId){
        LOGGER.info("ActionPointServiceImpl is running getIdeasOfActionPoint");
        ActionPoint actionPoint = actionPointRepository.findById(actionPointId).orElseThrow(EntityNotFoundException::new);
        List<Idea> ideas = actionPoint.getLinkedIdeas();
        LOGGER.info("ActionPointServiceImpl is returning idea list {}", ideas);
        return ideas;
    }

    @Override
    public ActionPoint getActionPointById(long youthCouncilId, long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running getActionPointById");
        ActionPoint actionPoint = actionPointRepository.findById(actionPointId).orElseThrow(EntityNotFoundException::new);
        if (actionPoint.getYouthCouncil().getId() == youthCouncilId){
            // youthCouncilId in URL must be for a youth council that owns the requested ActionPoint
            return actionPoint;
        }
        throw new EntityNotFoundException(String.format("ActionPoint with id %d does not belong to YouthCouncil with id %d", actionPointId, youthCouncilId));
    }

    @Override
    public List<String> getImagesOfActionPoint(long actionPointId) {
        LOGGER.info("ActionPointServiceImpl is running getImagesOfActionPoint");
        return actionPointRepository.getImagesByActionPointId(actionPointId);
    }
}
