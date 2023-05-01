package be.kdg.youth_council_project.controller.api;


import be.kdg.youth_council_project.controller.api.dtos.HomeYouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.NewYouthCouncilDto;
import be.kdg.youth_council_project.controller.api.dtos.YouthCouncilDto;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.YouthCouncilService;
import be.kdg.youth_council_project.tenants.NoTenantController;
import be.kdg.youth_council_project.util.FileUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@AllArgsConstructor
@ResponseBody
@NoTenantController
@RequestMapping("api/youth-councils")
public class YouthCouncilsController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final YouthCouncilService youthCouncilService;

    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ROLE_GENERAL_ADMINISTRATOR')")
    public ResponseEntity<YouthCouncilDto> addYouthCouncil(@RequestPart("youthCouncil") @Valid NewYouthCouncilDto newYouthCouncilDto,
                                                           @RequestPart("logo") MultipartFile logo) {
        LOGGER.info("YouthCouncilsController is running addYouthCouncil");
        if (!FileUtils.checkImageFile(logo)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        YouthCouncil createdYouthCouncil = new YouthCouncil();
        createdYouthCouncil.setName(newYouthCouncilDto.getName());
        createdYouthCouncil.setSlug(newYouthCouncilDto.getSubdomainName());
        System.out.println(newYouthCouncilDto.getMunicipalityName());
        youthCouncilService.setMunicipalityOfYouthCouncil(createdYouthCouncil, newYouthCouncilDto.getMunicipalityName());
        LOGGER.debug("content type: {}", logo.getContentType());
        try {
            youthCouncilService.setLogoOfYouthCouncil(createdYouthCouncil, logo.getBytes());
        } catch (IOException e) {
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


    @GetMapping("/map-data")
    public ResponseEntity<ByteArrayResource> getMapData() {
        LOGGER.info("IndexController is running getMapData");

        File file = new File("src/main/resources/static/json/Gemeenten.json");
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            ByteArrayResource resource = new ByteArrayResource(data);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resource);
        } catch (IOException e) {
            LOGGER.error("Failed to read file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping
    @Transactional
    public ResponseEntity<List<HomeYouthCouncilDto>> getAllYouthCouncils(
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        try {
            LOGGER.info("YouthCouncilsController is running getAllYouthCouncils");
            List<YouthCouncil> youthCouncils = youthCouncilService.getYouthCouncils();
            List<HomeYouthCouncilDto> youthCouncilDtos = youthCouncils.stream()
                    .map(youthCouncil -> new HomeYouthCouncilDto(
                            youthCouncil.getId(),
                            youthCouncil.getName(),
                            youthCouncil.getMunicipalityName(),
                            youthCouncil.getSlug(),
                            false
                    ))
                    .toList();
            if (youthCouncilDtos.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            if (currentUser == null)
                return new ResponseEntity<>(youthCouncilDtos, HttpStatus.OK);

            for (HomeYouthCouncilDto youthCouncilDto : youthCouncilDtos) {
                youthCouncilDto.setMember(youthCouncils.stream()
                        .anyMatch(youthCouncil -> youthCouncil.getMembers().stream()
                                .anyMatch(membership -> membership.getMembershipId().getUser().getId().equals(currentUser.getUserId()))));
            }
            return new ResponseEntity<>(youthCouncilDtos, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Failed to get all youth councils: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
