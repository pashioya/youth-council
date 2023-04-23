package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;


import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewNewsItemDto {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String image;
}
