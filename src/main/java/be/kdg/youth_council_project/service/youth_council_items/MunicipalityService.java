package be.kdg.youth_council_project.service.youth_council_items;

import be.kdg.youth_council_project.domain.platform.Municipality;

import java.util.List;

public interface MunicipalityService {
    List<Municipality> getMunicipalities();

    Municipality getMunicipalityByYouthCouncilId(long tenantId);
}
