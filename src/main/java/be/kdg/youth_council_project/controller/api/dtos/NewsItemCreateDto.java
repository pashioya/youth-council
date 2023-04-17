package be.kdg.youth_council_project.controller.api.dtos;


import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemCreateDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String image;
}
