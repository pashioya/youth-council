package be.kdg.youth_council_project.controller.mvc.viewmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElectionViewModel {
    private Long id;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;

    public String getParsedStartDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a");
        return startDate.format(dateFormatter);
    }

    public String getParsedEndDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a");
        return endDate.format(dateFormatter);
    }
}

