package Federation.Agricole.API.dto;

import lombok.Data;

@Data
public class CreateMemberPaymentDTO {
    private Double amount;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private String paymentMode;
}