package Federation.Agricole.API.service;

import Federation.Agricole.API.entity.Member;
import Federation.Agricole.API.entity.MemberPayment;
import Federation.Agricole.API.entity.Transaction;
import Federation.Agricole.API.exception.BadRequestException;
import Federation.Agricole.API.repository.MemberPaymentRepository;
import Federation.Agricole.API.repository.MemberRepository;
import Federation.Agricole.API.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static Federation.Agricole.API.entity.TransactionType.IN;
import static java.time.LocalDate.now;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberPaymentRepository memberPaymentRepository;
    private final TransactionRepository transactionRepository;

    public List<Member> addNewMembers(List<Member> memberList) {
        for (Member member : memberList) {
            if (!member.refereesAreEligible()) {
                throw new BadRequestException("Member.id=" + member.getId() + " member referees are not eligible");
            }
            if (!member.getMembershipDuesPaid()) {
                throw new BadRequestException("Member.id=" + member.getId() + " membership dues not paid");
            }
            if (!member.getRegistrationFeePaid()) {
                throw new BadRequestException("Member.id=" + member.getId() + " membership fees not paid");
            }
            member.setId(randomUUID().toString());
        }
        return memberRepository.saveAll(memberList);
    }

    public List<MemberPayment> createPayments(List<MemberPayment> memberPaymentList) {
        for (MemberPayment member : memberPaymentList) {
            member.setId(randomUUID().toString());
            member.setCreationDate(now());
        }
        List<MemberPayment> savedMemberPayments = memberPaymentRepository.saveAll(memberPaymentList);

        List<Transaction> newTransactionList = savedMemberPayments.stream()
                .map(memberPayment -> {
                    Transaction transaction = Transaction.builder()
                            .id(randomUUID().toString())
                            .memberDebited(memberPayment.getMemberOwner())
                            .amount(memberPayment.getAmount())
                            .type(IN)
                            .creationDate(memberPayment.getCreationDate())
                            .accountCredited(memberPayment.getAccountCredited())
                            .build();
                    return transaction;
                })
                .toList();

        transactionRepository.saveAll(newTransactionList);

        return savedMemberPayments;
    }
}
