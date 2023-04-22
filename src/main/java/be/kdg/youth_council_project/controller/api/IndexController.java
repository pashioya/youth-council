package be.kdg.youth_council_project.controller.api;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/index")
@AllArgsConstructor
public class IndexController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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


}
