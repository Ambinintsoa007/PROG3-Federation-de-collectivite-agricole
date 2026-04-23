package Federation.Agricole.API.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class AccountDTO {
    private String accountType;
    private Double amount;
    private String holderName;
    private String mobileBankingService;
    private Integer mobileNumber;
    private String bankName;
    private Integer bankCode;
    private Integer bankBranchCode;
    private Integer bankAccountNumber;
    private Integer bankAccountKey;
}