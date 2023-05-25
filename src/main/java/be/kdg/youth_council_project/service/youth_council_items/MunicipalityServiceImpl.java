package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.repository.MunicipalityRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MunicipalityServiceImpl implements MunicipalityService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final MunicipalityRepository municipalityRepository;


    @Override
    public List<Municipality> getAllMunicipalities() {
        LOGGER.info("MunicipalityServiceImpl is running getMunicipalities");
        return municipalityRepository.findAll();
    }

    @Override
    public Municipality getMunicipalityByYouthCouncilId(long tenantId) {
        LOGGER.info("MunicipalityServiceImpl is running getMunicipalitiesByYouthCouncilId");
        Optional<Municipality> municipality = municipalityRepository.getMunicipalityByYouthCouncilId(tenantId);
        return municipality.orElse(null);
    }
}
