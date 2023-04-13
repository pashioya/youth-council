package be.kdg.youth_council_project.controller.api.dtos;


import jdk.jfr.ContentType;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewYouthCouncilDto {
    private String name;
    private String municipalityName;
    private String subdomainName;

}
