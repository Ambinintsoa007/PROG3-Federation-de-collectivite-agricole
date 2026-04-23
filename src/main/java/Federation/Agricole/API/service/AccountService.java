package Federation.Agricole.API.service;

import Federation.Agricole.API.dto.AccountDTO;
import Federation.Agricole.API.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void createAccount(String collectivityId, AccountDTO dto) {
        if (dto.getAmount() != null && dto.getAmount() < 0) {
            throw new RuntimeException("Amount under 0");
        }
        String generatedId = "ACC-" + java.util.UUID.randomUUID().toString().substring(0, 8);
        accountRepository.save(generatedId, collectivityId, dto);
    }
}
