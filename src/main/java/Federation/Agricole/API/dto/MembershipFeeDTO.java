package Federation.Agricole.API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipFeeDTO {
    private String id;
    private String collectivityId;
    private LocalDate eligibleFrom;
    private String frequency;
    private Double amount;
    private String label;
    private String status;
}