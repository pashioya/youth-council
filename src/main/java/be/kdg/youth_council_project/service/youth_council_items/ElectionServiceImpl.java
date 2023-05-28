package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.controller.api.dtos.youth_council_items.ElectionDto;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Election;
import be.kdg.youth_council_project.repository.ElectionRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class ElectionServiceImpl implements ElectionService {


    private final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final ElectionRepository electionRepository;
    private final YouthCouncilRepository youthCouncilRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Election> getAllElectionsByYouthCouncilId(long youthCouncilId) {
        LOGGER.info("ElectionServiceImpl is running getAllElectionsByYouthCouncilId with youthCouncilId {}", youthCouncilId);
        return electionRepository.getAllElectionsByYouthCouncilId(youthCouncilId);
    }

    @Override
    public Election createElection(ElectionDto electionDto, long tenantId) {
        LOGGER.info("ElectionServiceImpl is running createElection with electionDto {}", electionDto);
        Election election = modelMapper.map(electionDto, Election.class);
        election.setYouthCouncil(youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new));
        election.setStartDate(electionDto.getStartDate());
        election.setEndDate(electionDto.getEndDate());
        return electionRepository.save(election);
    }

    @Override
    public ElectionDto mapToDto(Election election) {
        LOGGER.info("ElectionServiceImpl is running mapToDto with election {}", election);
        return modelMapper.map(election, ElectionDto.class);
    }

    @Override
    public LocalDateTime parseElectionDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(date, formatter);
    }

    @Override
    public void deleteElection(long id, long tenantId) {
        LOGGER.info("ElectionServiceImpl is running deleteElection with id {}", id);
        Election election = electionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        electionRepository.delete(election);
    }

    @Override
    public Election getElectionById(long electionId, long tenantId) {
        LOGGER.info("ElectionServiceImpl is running getElectionById with electionId {}", electionId);
        return electionRepository.findById(electionId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Election updateElection(ElectionDto electionDto, long tenantId) {
        LOGGER.info("ElectionServiceImpl is running updateElection with electionDto {}", electionDto);
        Election election = electionRepository.findById(electionDto.getId()).orElseThrow(EntityNotFoundException::new);
        Election newElection = modelMapper.map(electionDto, Election.class);
        newElection.setYouthCouncil(youthCouncilRepository.findById(tenantId).orElseThrow(EntityNotFoundException::new));
        LOGGER.info("ElectionServiceImpl is running updateElection with newElection {}", newElection);
        return electionRepository.save(newElection);
    }
}
