package Federation.Agricole.API.controller.mapper;

import Federation.Agricole.API.controller.dto.CollectivityTransaction;
import Federation.Agricole.API.controller.dto.PaymentMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.time.LocalDate.now;

@Component
@RequiredArgsConstructor
public class TransactionDtoMapper {
    private final FinancialAccountDtoMapper financialAccountDtoMapper;
    private final MemberDtoMapper memberDtoMapper;

    public CollectivityTransaction mapToDto(Federation.Agricole.API.entity.CollectivityTransaction collectivityTransaction) {
        return CollectivityTransaction.builder()
                .id(collectivityTransaction.getId())
                .amount(collectivityTransaction.getAmount())
                .creationDate(collectivityTransaction.getCreationDate())
                .paymentMode(collectivityTransaction.getPaymentMode() == null ? null : PaymentMode.valueOf(collectivityTransaction.getPaymentMode().name()))
                .accountCredited(financialAccountDtoMapper.mapToDto(collectivityTransaction.getAccountCredited()))
                .memberDebited(memberDtoMapper.mapToDto(collectivityTransaction.getMemberDebited()))
                .build();
    }
}
