package be.kdg.youth_council_project.domain.platform.youth_council_items;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StandardAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;
    private String name;

    @OneToMany(orphanRemoval = true, mappedBy = "linkedStandardAction", cascade = CascadeType.PERSIST)
    private List<ActionPoint> actionPoints;


    @PreRemove
    private void preRemove() {
        for (ActionPoint actionPoint : actionPoints) {
            actionPoint.setLinkedStandardAction(null);
        }
    }
}
