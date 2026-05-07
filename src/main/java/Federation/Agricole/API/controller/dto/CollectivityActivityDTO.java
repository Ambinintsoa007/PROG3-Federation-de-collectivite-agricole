package Federation.Agricole.API.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectivityActivityDTO {
    private String id;
    private String label;
    private String activityType;
    private List<String> memberOccupationConcerned;
    private LocalDate executiveDate;
    private MonthlyRecurrenceRule recurrenceRule;

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyRecurrenceRule {
        private Integer weekOrdinal;
        private String dayOfWeek;
    }
}