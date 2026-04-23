package Federation.Agricole.API.controller;

import Federation.Agricole.API.dto.TransactionDTO;
import Federation.Agricole.API.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/collectivities/{id}/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @PathVariable String id,
            @RequestParam String from,
            @RequestParam String to) {

        List<TransactionDTO> result = transactionService.getTransactions(id, from, to);
        return ResponseEntity.ok(result);
    }
}