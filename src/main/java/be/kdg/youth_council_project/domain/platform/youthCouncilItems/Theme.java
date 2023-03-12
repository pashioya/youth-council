package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Theme {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "theme")
    private List<StandardAction> standardActions;


    public void addStandardAction(StandardAction standardAction) {
        standardActions.add(standardAction);
    }
}
