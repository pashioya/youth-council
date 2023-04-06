package be.kdg.youth_council_project.domain.platform.youth_council_items;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import javax.persistence.*;

import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToMany(mappedBy="linkedIdeas", cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    private List<ActionPoint> inspiredActionPoints;

    @ManyToOne
    @JoinColumn(name="theme_id")
    private Theme theme;
    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name="idea_image", joinColumns=@JoinColumn(name="idea_id"))
    @Column(name="image")
    private List<String> images;
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

    @OneToMany(mappedBy="idea", orphanRemoval = true)
    private List<IdeaComment> comments;

    @OneToMany(mappedBy="ideaLikeId.idea", orphanRemoval = true)
    private List<IdeaLike> likes;

    public Idea(String description, List<String> images) {
        this.description = description;
        this.images = images;
        this.createdDate =LocalDateTime.now();
        this.inspiredActionPoints = new ArrayList<>();
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
