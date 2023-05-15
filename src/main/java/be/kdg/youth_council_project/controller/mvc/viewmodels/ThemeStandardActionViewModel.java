package be.kdg.youth_council_project.controller.mvc.viewmodels;

import be.kdg.youth_council_project.domain.platform.youth_council_items.StandardAction;
import be.kdg.youth_council_project.domain.platform.youth_council_items.Theme;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThemeStandardActionViewModel {
    private Long id;
    private Theme theme;
    private List<StandardAction> standardActions;

}
