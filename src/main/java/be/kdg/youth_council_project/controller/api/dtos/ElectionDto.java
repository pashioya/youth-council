package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ElectionDto {
    private String title;
    private String description;
    private String location;
    private String startDate;
    private String endDate;
    private String isActive;
}
