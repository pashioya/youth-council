package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;

import java.util.List;

public interface YouthCouncilService {
    YouthCouncil getYouthCouncilById(long id);

    void setMunicipalityOfYouthCouncil(YouthCouncil createdYouthCouncil, String municipalityName);

    void setLogoOfYouthCouncil(YouthCouncil createdYouthCouncil, byte[] logo);

    YouthCouncil createYouthCouncil(YouthCouncil createdYouthCouncil);

    List<YouthCouncil> getYouthCouncils();

    YouthCouncil getYouthCouncilBySlug(String slug);
}
