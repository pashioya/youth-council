package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import be.kdg.youth_council_project.domain.platform.User;
import be.kdg.youth_council_project.domain.platform.YouthCouncil;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

import java.time.LocalDateTime;
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

    @ManyToMany(mappedBy="linkedIdeas")
    private List<ActionPoint> actionPoint;

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

    public Idea(String description, List<String> images) {
        this.description = description;
        this.images = images;
        this.createdDate =LocalDateTime.now();
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
}
