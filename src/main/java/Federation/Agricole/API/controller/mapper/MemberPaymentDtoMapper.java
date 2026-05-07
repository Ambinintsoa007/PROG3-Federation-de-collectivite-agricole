package Federation.Agricole.API.controller.mapper;

import Federation.Agricole.API.controller.dto.CreateMemberPayment;
import Federation.Agricole.API.entity.*;
import Federation.Agricole.API.exception.NotFoundException;
import Federation.Agricole.API.repository.FinancialAccountRepository;
import Federation.Agricole.API.repository.MemberRepository;
import Federation.Agricole.API.repository.MembershipFeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberPaymentDtoMapper {
    private final FinancialAccountDtoMapper financialAccountDtoMapper;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final FinancialAccountRepository financialAccountRepository;

    public MemberPayment mapToEntity(String memberIdentifier, CreateMemberPayment createMemberPayment) {
        Member member = memberRepository.findById(memberIdentifier).orElseThrow(
                () -> new NotFoundException("Member.id=" + memberIdentifier + " not found")
        );
        MembershipFee membershipFee = membershipFeeRepository.findById(createMemberPayment.getMembershipFeeIdentifier())
                .orElseThrow(
                        () -> new NotFoundException("MembershipFee.id=" + createMemberPayment.getMembershipFeeIdentifier() + " not found")
                );
        FinancialAccount financialAccount = financialAccountRepository.findFinancialAccountById(createMemberPayment.getAccountCreditedIdentifier())
                .orElseThrow(() -> new NotFoundException("FinancialAccount.id=" + createMemberPayment.getAccountCreditedIdentifier() + " not found"));

        return MemberPayment.builder()
                .paymentMode(createMemberPayment.getPaymentMode() == null ? null : PaymentMode.valueOf(createMemberPayment.getPaymentMode().name()))
                .amount(createMemberPayment.getAmount())
                .memberOwner(member)
                .membershipFee(membershipFee)
                .accountCredited(financialAccount)
                .build();
    }

    public Federation.Agricole.API.controller.dto.MemberPayment mapToDto(MemberPayment memberPayment) {
        return Federation.Agricole.API.controller.dto.MemberPayment.builder()
                .id(memberPayment.getId())
                .paymentMode(memberPayment.getPaymentMode() == null ? null : Federation.Agricole.API.controller.dto.PaymentMode.valueOf(memberPayment.getPaymentMode().name()))
                .accountCredited(financialAccountDtoMapper.mapToDto(memberPayment.getAccountCredited()))
                .creationDate(memberPayment.getCreationDate())
                .amount(memberPayment.getAmount())
                .build();
    }
}
