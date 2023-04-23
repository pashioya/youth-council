package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

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
