package be.kdg.youth_council_project.domain.platform.youth_council_items;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.NewsItemComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.NewsItemLike;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @Lob
    @ToString.Exclude
    private byte[] image;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @OneToMany(mappedBy = "newsItem", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<NewsItemComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "id.newsItem", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<NewsItemLike> likes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "youth_council_id")
    private YouthCouncil youthCouncil;

    public NewsItem(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }
}
