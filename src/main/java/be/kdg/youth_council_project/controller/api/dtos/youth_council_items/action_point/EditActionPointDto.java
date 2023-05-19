package be.kdg.youth_council_project.controller.api.dtos.youth_council_items.action_point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class EditActionPointDto {
    private long id;
    private String status;
    private long StandardActionId;
    private List<Long> linkedIdeasIds;
    @NotBlank
    @Size(min = 3, max = 150)
    private String title;
    @NotBlank
    @Size(min = 3, max = 500)
    private String description;
}

