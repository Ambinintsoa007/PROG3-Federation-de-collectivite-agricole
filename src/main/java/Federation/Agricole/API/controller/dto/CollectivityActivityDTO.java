package Federation.Agricole.API.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class CollectivityActivityDTO {
    private String id;
    private String label;
    private String activityType;
    private List<String> memberOccupationConcerned;
    private LocalDate executiveDate;

    public CollectivityActivityDTO() {
        this.id = id;
        this.label = label;
        this.activityType = activityType;
        this.executiveDate = executiveDate;
    }
}
