package be.kdg.youth_council_project.domain.platform.youth_council_items;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.IdeaImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Idea {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;

    @ManyToMany(mappedBy="linkedIdeas",
            fetch=FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ActionPoint> inspiredActionPoints;

    @ManyToOne
    @JoinColumn(name="theme_id")
    private Theme theme;

    @OneToMany(mappedBy="idea", orphanRemoval = true)
    @ToString.Exclude
    private List<IdeaImage> images;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

    @OneToMany(mappedBy="idea", orphanRemoval = true)
    private List<IdeaComment> comments;

    @OneToMany(mappedBy="id.idea", orphanRemoval = true)
    private List<IdeaLike> likes;

    public Idea(String description) {
        this.description = description;
        this.createdDate =LocalDateTime.now();
        this.inspiredActionPoints = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Idea idea = (Idea) o;
        return Objects.equals(id, idea.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Idea{" +
                "description='" + description + '\'' +
                '}';
    }

    public void addActionPoint(ActionPoint actionPoint){
        inspiredActionPoints.add(actionPoint);
    }
}
