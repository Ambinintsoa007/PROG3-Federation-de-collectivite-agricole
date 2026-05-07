package Federation.Agricole.API.controller.mapper;

import Federation.Agricole.API.controller.dto.Bank;
import Federation.Agricole.API.controller.dto.FinancialAccount;
import Federation.Agricole.API.controller.dto.MobileBankingService;
import Federation.Agricole.API.entity.BankAccount;
import Federation.Agricole.API.entity.CashAccount;
import Federation.Agricole.API.entity.MobileBankingAccount;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Component
public class FinancialAccountDtoMapper {
    public FinancialAccount mapToDto(Federation.Agricole.API.entity.FinancialAccount financialAccount, LocalDate at) {
        LocalDate balanceAt = at == null ? now() : at;
        if (financialAccount instanceof CashAccount cashAccount) {
            return Federation.Agricole.API.controller.dto.CashAccount.builder()
                    .id(cashAccount.getId())
                    .amount(cashAccount.getBalanceAt(balanceAt).intValue())
                    .build();
        } else if (financialAccount instanceof BankAccount bankAccount) {
            return Federation.Agricole.API.controller.dto.BankAccount.builder()
                    .id(bankAccount.getId())
                    .holderName(bankAccount.getHolderName())
                    .bankName(bankAccount.getBankName() == null ? null : Bank.valueOf(bankAccount.getBankName().name()))
                    .bankCode(bankAccount.getBankCode())
                    .bankBranchCode(bankAccount.getBranchCode())
                    .bankAccountNumber(bankAccount.getAccountNumber())
                    .bankAccountKey(bankAccount.getAccountKey())
                    .amount(bankAccount.getBalanceAt(balanceAt))
                    .build();
        } else if (financialAccount instanceof MobileBankingAccount mobileBankingAccount) {
            return Federation.Agricole.API.controller.dto.MobileBankingAccount.builder()
                    .id(mobileBankingAccount.getId())
                    .holderName(mobileBankingAccount.getHolderName())
                    .mobileNumber(Integer.parseInt(mobileBankingAccount.getMobileNumber().replaceAll("[^0-9]", "")))
                    .mobileBankingService(mobileBankingAccount.getMobileBankingService() == null ? null : MobileBankingService.valueOf(mobileBankingAccount.getMobileBankingService().name()))
                    .amount(mobileBankingAccount.getBalanceAt(balanceAt))
                    .build();
        }
        throw new IllegalArgumentException("Unknown financial account type " + financialAccount.getClass().getName());
    }

    public FinancialAccount mapToDto(Federation.Agricole.API.entity.FinancialAccount financialAccount) {
        return  mapToDto(financialAccount, now());
    }

}
