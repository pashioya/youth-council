package be.kdg.youth_council_project.domain.platform.youth_council_items.comments;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.youth_council_items.NewsItem;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NewsItemComment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User author;
    private String content;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="newsItem_id")
    private NewsItem newsItem;


    private LocalDateTime createdDate;
}
