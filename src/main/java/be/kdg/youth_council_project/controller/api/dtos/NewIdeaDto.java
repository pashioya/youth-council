package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewIdeaDto {

    @NotBlank
    private String description;
    private long themeId;
}
