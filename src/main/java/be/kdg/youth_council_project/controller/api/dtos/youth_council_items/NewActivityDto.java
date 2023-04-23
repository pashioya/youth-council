package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewActivityDto {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
