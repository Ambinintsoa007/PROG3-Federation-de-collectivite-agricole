package Federation.Agricole.API.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CreateMembershipFeeDTO {
    private String eligibleFrom;
    private String frequency;
    private Double amount;
    private String label;
}
