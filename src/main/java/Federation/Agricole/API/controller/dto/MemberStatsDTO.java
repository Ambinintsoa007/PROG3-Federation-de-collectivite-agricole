package Federation.Agricole.API.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@NoArgsConstructor
public class MemberStatsDTO {
    private MemberDescription memberDescription;
    private Double earnedAmount;
    private Double unpaidAmount;

    public MemberStatsDTO(MemberDescription memberDescription, Double earnedAmount, Double unpaidAmount) {
        this.memberDescription = memberDescription;
        this.earnedAmount = earnedAmount;
        this.unpaidAmount = unpaidAmount;
    }
}
