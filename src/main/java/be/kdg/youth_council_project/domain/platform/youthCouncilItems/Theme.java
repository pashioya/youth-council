package be.kdg.youth_council_project.domain.platform.youthCouncilItems;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Theme {

    private Long id;
    private String name;

    private List<StandardAction> standardActions;


}
