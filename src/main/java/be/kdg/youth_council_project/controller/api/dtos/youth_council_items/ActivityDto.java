package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDto {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private String description;
}
