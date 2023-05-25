package be.kdg.youth_council_project.service;

import be.kdg.youth_council_project.domain.platform.Municipality;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.repository.MunicipalityRepository;
import be.kdg.youth_council_project.repository.SocialMediaLinkRepository;
import be.kdg.youth_council_project.repository.YouthCouncilRepository;
import be.kdg.youth_council_project.service.webpage.WebPageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class YouthCouncilServiceImpl implements YouthCouncilService{
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final WebPageService webPageService;
    private final YouthCouncilRepository youthCouncilRepository;
    private final MunicipalityRepository municipalityRepository;
    private final SocialMediaLinkRepository socialMediaLinkRepository;
    @Override
    public YouthCouncil getYouthCouncilById(long id) {
        LOGGER.info("YouthCouncilServiceImpl is running getYouthCouncilById");
        return youthCouncilRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void setMunicipalityOfYouthCouncil(YouthCouncil youthCouncil, String municipalityName) {
        LOGGER.info("YouthCouncilServiceImpl is running setMunicipalityOfYouthCouncil with municipality name {}", municipalityName);
        Municipality municipality = municipalityRepository.findByNameIgnoreCase(municipalityName).orElseThrow(EntityNotFoundException::new);
        youthCouncil.setMunicipality(municipality);
    }

    @Override
    public void setLogoOfYouthCouncil(YouthCouncil youthCouncil, byte[] logo) {
        LOGGER.info("YouthCouncilServiceImpl is running setLogoOfYouthCouncil");
        youthCouncil.setLogo(logo);
    }

    @Override
    @Transactional
    public YouthCouncil createYouthCouncil(YouthCouncil youthCouncil) {
        LOGGER.info("YouthCouncilServiceImpl is running createYouthCouncil");
        YouthCouncil savedYouthCouncil = youthCouncilRepository.save(youthCouncil);
        webPageService.createHomePageOfYouthCouncil(youthCouncil);
        LOGGER.debug("Saved youth council {}", savedYouthCouncil);
        return savedYouthCouncil;
    }

    @Override
    public List<YouthCouncil> getYouthCouncils() {
        List<YouthCouncil> youthCouncils =  youthCouncilRepository.findAll();
        youthCouncils.forEach(youthCouncil -> youthCouncil.setMunicipality(
                municipalityRepository.getMunicipalityOfYouthCouncilByYouthCouncilId(youthCouncil.getId())
                        .get()
        )
        );
        return youthCouncils;
    }

    @Override
    public YouthCouncil getYouthCouncilBySlug(String slug) {
        return youthCouncilRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
    }
}
