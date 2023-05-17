package be.kdg.youth_council_project.domain.platform.youth_council_items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Theme {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "theme")
    List<Idea> ideas;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "theme")
    List<StandardAction> standardActions;

    public Theme(String name) {
        this.name = name;
    }
}
