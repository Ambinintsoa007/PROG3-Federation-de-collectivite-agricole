package Federation.Agricole.API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private String id;
    private LocalDate creationDate;
    private Double amount;
    private String paymentMode;
    private String accountCreditedId;
}