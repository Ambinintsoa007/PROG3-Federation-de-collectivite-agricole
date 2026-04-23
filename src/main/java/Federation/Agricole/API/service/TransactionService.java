package Federation.Agricole.API.service;

import Federation.Agricole.API.dto.TransactionDTO;
import Federation.Agricole.API.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionDTO> getTransactions(String id, String from, String to) {
        return transactionRepository.findByPeriod(id, from, to);
    }
}