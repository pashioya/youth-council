package be.kdg.youth_council_project.controller.api.dtos;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewActionPointDto {

    private String statusName;
    private String title;
    private String video;
    private String description;

    private List<String> images;

    private List<Long> linkedIdeaIds;

    private Long standardActionId;
}
