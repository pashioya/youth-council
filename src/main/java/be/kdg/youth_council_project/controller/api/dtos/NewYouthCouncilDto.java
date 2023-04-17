package be.kdg.youth_council_project.controller.api.dtos;


import jdk.jfr.ContentType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewYouthCouncilDto {

    @NotBlank
    private String name;

    @NotBlank
    private String municipalityName;

    @NotBlank
    private String subdomainName;

}
