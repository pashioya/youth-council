package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Map;


@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewInformativePageTemplateDto {

    @NotBlank
    private String title;
    private Map<String, String> headingsBodies;
}
