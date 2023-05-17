package be.kdg.youth_council_project.domain.platform.youth_council_items;

import javax.persistence.*;
import lombok.*;

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

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "linkedStandardAction")
    List<ActionPoint> actionPoints;
}
