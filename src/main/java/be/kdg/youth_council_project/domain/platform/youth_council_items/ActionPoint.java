package be.kdg.youth_council_project.domain.platform.youth_council_items;

import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import be.kdg.youth_council_project.domain.platform.youth_council_items.comments.ActionPointComment;
import be.kdg.youth_council_project.domain.platform.youth_council_items.images.ActionPointImage;
import be.kdg.youth_council_project.domain.platform.youth_council_items.like.ActionPointLike;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank
    @Size(min=3, max=80)
    private String title;
    private String video;

    @NotBlank
    @Size(min=3, max=1000)
    private String description;

    @OneToMany(mappedBy="actionPoint", orphanRemoval = true)
    @ToString.Exclude
    private List<ActionPointImage> images;
    private LocalDateTime createdDate;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="action_points_linked_ideas",
            joinColumns = @JoinColumn(name = "action_point_id"),
            inverseJoinColumns = @JoinColumn(name="idea_id"))
    private List<Idea> linkedIdeas;

    @ManyToOne
    @JoinColumn(name = "standardaction_id")
    private StandardAction linkedStandardAction;

    @ManyToOne
    @JoinColumn(name="youth_council_id")
    private YouthCouncil youthCouncil;

    @OneToMany(mappedBy="actionPoint", orphanRemoval = true)
    private List<ActionPointComment> comments;

    @OneToMany(mappedBy="id.actionPoint", orphanRemoval = true)
    private List<ActionPointLike> likes;

    public ActionPoint(String title, String video, String description, List<ActionPointImage> actionPointImages,
                       LocalDateTime createdDate) {
        this.title = title;
        this.video = video;
        this.description = description;
        this.images = actionPointImages;
        this.createdDate = createdDate;
        this.linkedIdeas=new ArrayList<>();
    }

    public ActionPoint(String title, String video, String description, LocalDateTime now) {
        this.title = title;
        this.video = video;
        this.description = description;
        this.createdDate = now;
        this.linkedIdeas=new ArrayList<>();
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

    public void addImage(ActionPointImage actionPointImage) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(actionPointImage);
    }

}
