package be.kdg.youth_council_project.controller.api.dtos.youth_council_items;

import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsItemDto {
    private long id;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private String author;
}
