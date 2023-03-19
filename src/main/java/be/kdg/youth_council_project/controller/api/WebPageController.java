package be.kdg.youth_council_project.controller.api;

import be.kdg.youth_council_project.controller.api.dtos.WebPageDto;
import be.kdg.youth_council_project.security.CustomUserDetails;
import be.kdg.youth_council_project.service.WebPageService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/youth-councils/{id}/webpage")
public class WebPageController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final WebPageService webPageService;

    @GetMapping
    public ResponseEntity<WebPageDto> getWebPage(@PathVariable("id") long youthCouncilId, @AuthenticationPrincipal CustomUserDetails user) {
        LOGGER.info("WebPageController is running getWebPage");
        ModelMapper modelMapper = new ModelMapper();
        WebPageDto webPageDto = modelMapper.map(webPageService.getWebPageByYouthCouncilId(youthCouncilId), WebPageDto.class);
        // check if user has admin role
        if (user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_YOUTH_COUNCIL_ADMIN"))) {
            webPageDto.setAdminDashboardEnabled(true);
        }
        return ResponseEntity.ok(webPageDto);
    }

}
