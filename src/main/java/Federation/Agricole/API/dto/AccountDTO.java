package Federation.Agricole.API.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class AccountDTO {
    private String accountType;
    private String holderName;
    private String bankName;
    private String mobileNumber;
}
