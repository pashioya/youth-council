package be.kdg.youth_council_project.domain.platform.youth_council_items;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import javax.persistence.*;

import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.IdeaComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.IdeaLike;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActionPoint {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionPointStatus status;
    private String title;
    private String video;
    private String description;

    @ToString.Exclude
    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(name="action_point_image", joinColumns=@JoinColumn(name="action_point_id"))
    @Column(name="image")
    private List<String> images;
    private LocalDateTime createdDate;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    @JoinTable(name="action_points_linked_ideas",
            joinColumns = @JoinColumn(name = "action_point_id"),
            inverseJoinColumns = @JoinColumn(name="idea_id"))
    private List<Idea> linkedIdeas;
    @ManyToOne
    @JoinColumn(name="standardaction_id")
    private StandardAction linkedStandardAction;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

    @OneToMany(mappedBy="actionPoint")
    private List<ActionPointComment> comments;

    @OneToMany(mappedBy="id.actionPoint")
    private List<ActionPointLike> likes;

    public ActionPoint(String title, String video, String description, List<String> images, LocalDateTime createdDate) {
        this.title = title;
        this.video = video;
        this.description = description;
        this.images = images;
        this.createdDate = createdDate;
        this.linkedIdeas=new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ActionPoint{" +
                "title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionPoint that = (ActionPoint) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addLinkedIdea(Idea idea) {
        linkedIdeas.add(idea);
    }
}
