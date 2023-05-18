package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatsUserDto {
    private Long id;
    private LocalDateTime createdDate;
}
