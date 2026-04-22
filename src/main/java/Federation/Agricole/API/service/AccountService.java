package Federation.Agricole.API.service;

import Federation.Agricole.API.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public void createAccount(String collectivityId, String type, String holder, String bank, String mobile) {

        if (collectivityId == null || collectivityId.isEmpty()) {
            throw new RuntimeException("Collectivity ID is required.");
        }

        String generatedId = "ACC-" + System.currentTimeMillis();

        accountRepository.save(generatedId, collectivityId, type, holder, bank, mobile);
    }
}
