package be.kdg.youth_council_project.controller.mvc.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionViewModel {
    private Long id;
    private String title;
    private String description;
    private String location;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private boolean isActive;
}
