package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemDto {
    private String title;
    private String content;
}
