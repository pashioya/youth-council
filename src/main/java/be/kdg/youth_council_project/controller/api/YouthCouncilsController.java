package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.*;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Idea;
import be.kdg.youth_council_project.service.YouthCouncilService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import be.kdg.youth_council_project.util.FileUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@AllArgsConstructor
@ResponseBody
@NoTenantController
public class YouthCouncilsController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final YouthCouncilService youthCouncilService;

    @PostMapping(path = "/api/youth-councils", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ResponseEntity<YouthCouncilDto> addYouthCouncil(@RequestPart("youthCouncil") @Valid NewYouthCouncilDto newYouthCouncilDto,
                                                           @RequestPart("logo") MultipartFile logo) {
        LOGGER.info("YouthCouncilsController is running addYouthCouncil");
        if (!FileUtils.checkImageFile(logo)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        };
        YouthCouncil createdYouthCouncil = new YouthCouncil();
        createdYouthCouncil.setName(newYouthCouncilDto.getName());
        createdYouthCouncil.setSlug(newYouthCouncilDto.getSubdomainName());
        youthCouncilService.setMunicipalityOfYouthCouncil(createdYouthCouncil, newYouthCouncilDto.getMunicipalityName());
        LOGGER.debug("content type: {}", logo.getContentType());
        try {
            youthCouncilService.setLogoOfYouthCouncil(createdYouthCouncil, logo.getBytes());
        } catch (IOException e){
            e.printStackTrace();
        }
        youthCouncilService.createYouthCouncil(createdYouthCouncil);
        return new ResponseEntity<>(
                new YouthCouncilDto(
                        createdYouthCouncil.getId(),
                        createdYouthCouncil.getName(),
                        createdYouthCouncil.getMunicipalityName()
                ),
                HttpStatus.CREATED);
    }
}
