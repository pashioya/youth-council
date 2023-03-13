package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Theme {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "theme", fetch = FetchType.EAGER)
    private List<StandardAction> standardActions;


    public void addStandardAction(StandardAction standardAction) {
        standardActions.add(standardAction);
    }
}
