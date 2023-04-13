package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewIdeaDto {
    private String description;
    private long themeId;
}
