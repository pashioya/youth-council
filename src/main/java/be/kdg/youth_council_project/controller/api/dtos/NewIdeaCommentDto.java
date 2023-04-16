package be.kdg.youth_council_project.controller.api.dtos;


import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewIdeaCommentDto {

    @NotBlank
    private String content;
}
