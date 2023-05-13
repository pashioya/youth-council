package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.repository.MunicipalityRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MunicipalityServiceImpl implements MunicipalityService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final MunicipalityRepository municipalityRepository;


    @Override
    public List<Municipality> getMunicipalities() {
        LOGGER.info("MunicipalityServiceImpl is running getMunicipalities");
        return municipalityRepository.findAll();
    }

    @Override
    public Municipality getMunicipalitiesByYouthCouncilId(long tenantId) {
        LOGGER.info("MunicipalityServiceImpl is running getMunicipalitiesByYouthCouncilId");
        return municipalityRepository.getMunicipalityByYouthCouncilId(tenantId);
    }
}
